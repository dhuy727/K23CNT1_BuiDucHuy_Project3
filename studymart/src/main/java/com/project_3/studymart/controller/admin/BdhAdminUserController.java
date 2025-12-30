package com.project_3.studymart.controller.admin;

import com.project_3.studymart.dto.BdhRegisterRequest;
import com.project_3.studymart.service.BdhUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class BdhAdminUserController {

    private final BdhUserService userService;

    @GetMapping("/create-admin")
    public String createAdminForm(Model model) {
        model.addAttribute("form", new BdhRegisterRequest());
        model.addAttribute("pageTitle", "Tạo tài khoản Admin");
        model.addAttribute("pageSubtitle", "Chỉ quản trị viên mới có quyền tạo admin");
        return "admin/create-admin";
    }

    @PostMapping("/create-admin")
    public String createAdminSubmit(@ModelAttribute("form") BdhRegisterRequest form, Model model) {
        try {
            userService.createAdmin(form);
            return "redirect:/admin/create-admin?ok";
        } catch (Exception e) {
            model.addAttribute("err", e.getMessage());
            model.addAttribute("pageTitle", "Tạo tài khoản Admin");
            model.addAttribute("pageSubtitle", "Chỉ quản trị viên mới có quyền tạo admin");
            return "admin/create-admin";
        }
    }

    @GetMapping("/users")
    public String usersIndex(@RequestParam(required = false) String q,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             Model model) {

        var p = userService.adminListUsers(q, page, size);

        model.addAttribute("pageTitle", "Quản lý tài khoản");
        model.addAttribute("pageSubtitle", "Tìm kiếm, khóa/mở, phân quyền USER/ADMIN");
        model.addAttribute("q", q);
        model.addAttribute("p", p);
        return "admin/users";
    }

    @PostMapping("/users/{id}/active")
    public String setActive(@PathVariable Long id,
                            @RequestParam boolean active,
                            @RequestParam(required = false) String q,
                            @RequestParam(defaultValue = "0") int page,
                            Authentication auth) {
        userService.adminSetActive(id, active, auth.getName());
        return "redirect:/admin/users?q=" + (q == null ? "" : q) + "&page=" + page;
    }

    @PostMapping("/users/{id}/role")
    public String setRole(@PathVariable Long id,
                          @RequestParam String role,
                          @RequestParam(required = false) String q,
                          @RequestParam(defaultValue = "0") int page,
                          Authentication auth,
                          RedirectAttributes redirectAttributes) {
        try {
            userService.adminSetRole(id, role, auth.getName());
            redirectAttributes.addFlashAttribute("bdhMsg", "Cập nhật quyền thành công.");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("bdhError", ex.getMessage());
        }

        redirectAttributes.addAttribute("q", q == null ? "" : q);
        redirectAttributes.addAttribute("page", page);

        return "redirect:/admin/users";
    }


    @GetMapping("/users/create")
    public String createUserForm(Model model) {
        model.addAttribute("form", new BdhRegisterRequest());
        model.addAttribute("pageTitle", "Tạo tài khoản USER");
        model.addAttribute("pageSubtitle", "Tạo tài khoản người dùng (USER)");
        return "admin/user-create";
    }

    @PostMapping("/users/create")
    public String createUserSubmit(@ModelAttribute("form") BdhRegisterRequest form, Model model) {
        try {
            userService.adminCreateUser(form);
            return "redirect:/admin/users?okCreate";
        } catch (Exception e) {
            model.addAttribute("err", e.getMessage());
            model.addAttribute("pageTitle", "Tạo tài khoản USER");
            model.addAttribute("pageSubtitle", "Tạo tài khoản người dùng (USER)");
            return "admin/user-create";
        }
    }

    @PostMapping("/{id}/role")
    public String updateUserRole(@PathVariable Long id,
                                 @RequestParam String role,
                                 @RequestParam(required = false) String q,
                                 @RequestParam(defaultValue = "0") int page,
                                 Authentication auth,
                                 RedirectAttributes redirectAttributes) {
        try {
            userService.adminSetRole(id, role, auth.getName());
            redirectAttributes.addFlashAttribute("bdhMsg",
                    "Cập nhật quyền người dùng thành công.");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("bdhError",
                    "Cập nhật quyền thất bại: " + ex.getMessage());
        }

        return "redirect:/admin/users?q=" + (q == null ? "" : q) + "&page=" + page;
    }


    @GetMapping("/users/{id}/edit")
    public String editUserForm(@PathVariable Long id, Model model) {
        var u = userService.adminGetUser(id);

        BdhRegisterRequest form = new BdhRegisterRequest();
        form.setFullName(u.getFullName());
        form.setEmail(u.getEmail());
        form.setPhone(u.getPhone());
        form.setAddress(u.getAddress());

        model.addAttribute("userId", id);
        model.addAttribute("username", u.getUsername());
        model.addAttribute("form", form);
        model.addAttribute("pageTitle", "Sửa tài khoản");
        model.addAttribute("pageSubtitle", "Cập nhật thông tin / đổi mật khẩu (nếu nhập)");
        return "admin/user-edit";
    }

    @PostMapping("/users/{id}/edit")
    public String editUserSubmit(@PathVariable Long id,
                                 @ModelAttribute("form") BdhRegisterRequest form,
                                 Model model) {
        try {
            userService.adminUpdateUser(id, form);
            return "redirect:/admin/users?okUpdate";
        } catch (Exception e) {
            model.addAttribute("err", e.getMessage());
            model.addAttribute("userId", id);
            model.addAttribute("pageTitle", "Sửa tài khoản");
            model.addAttribute("pageSubtitle", "Cập nhật thông tin / đổi mật khẩu (nếu nhập)");
            return "admin/user-edit";
        }
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id,
                             @RequestParam(required = false) String q,
                             @RequestParam(defaultValue = "0") int page,
                             Authentication auth,
                             RedirectAttributes redirectAttributes) {
        try {
            userService.adminDeleteUserHardA(id, auth.getName());
            redirectAttributes.addFlashAttribute("bdhMsg", "Xóa tài khoản thành công.");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("bdhError", ex.getMessage());
        }

        return "redirect:/admin/users?q=" + (q == null ? "" : q) + "&page=" + page;
    }
}
