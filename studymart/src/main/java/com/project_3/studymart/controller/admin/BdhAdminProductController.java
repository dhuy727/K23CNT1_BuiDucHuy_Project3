package com.project_3.studymart.controller.admin;

import com.project_3.studymart.dto.BdhProductForm;
import com.project_3.studymart.entity.BdhCategory;
import com.project_3.studymart.entity.BdhProduct;
import com.project_3.studymart.service.BdhCategoryService;
import com.project_3.studymart.service.BdhProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class BdhAdminProductController {

    private final BdhProductService productService;
    private final BdhCategoryService categoryService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("pageTitle", "Quản lý sản phẩm");
        model.addAttribute("pageSubtitle", "Thêm / sửa / xóa sản phẩm");
        model.addAttribute("products", productService.getAll());
        return "admin/products";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("pageTitle", "Thêm sản phẩm");
        model.addAttribute("pageSubtitle", "Tạo mới sản phẩm");
        model.addAttribute("categories", categoryService.getAll());

        BdhProductForm form = new BdhProductForm();
        form.setActive(true);
        model.addAttribute("form", form);
        model.addAttribute("mode", "create");
        return "admin/product-form";
    }

    @PostMapping
    public String create(@ModelAttribute("form") BdhProductForm form) {
        BdhProduct p = new BdhProduct();
        fillEntityFromForm(form, p);

        productService.create(p);
        return "redirect:/admin/products";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        BdhProduct p = productService.getById(id);

        model.addAttribute("pageTitle", "Sửa sản phẩm");
        model.addAttribute("pageSubtitle", "Cập nhật thông tin sản phẩm");
        model.addAttribute("categories", categoryService.getAll());

        BdhProductForm form = new BdhProductForm();
        form.setId(p.getId());
        form.setName(p.getName());
        form.setDescription(p.getDescription());
        form.setPrice(p.getPrice());
        form.setQuantity(p.getQuantity());
        form.setBrand(p.getBrand());
        form.setImageUrl(p.getImageUrl());
        form.setActive(p.getActive());
            form.setCategoryId(p.getCategory() != null ? p.getCategory().getId() : null);

        model.addAttribute("form", form);
        model.addAttribute("mode", "edit");
        return "admin/product-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("form") BdhProductForm form) {
        BdhProduct p = new BdhProduct();
        fillEntityFromForm(form, p);
        productService.update(id, p);
        return "redirect:/admin/products";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/admin/products";
    }

    private void fillEntityFromForm(BdhProductForm form, BdhProduct p) {
        p.setName(form.getName());
        p.setDescription(form.getDescription());
        p.setPrice(form.getPrice());
        p.setQuantity(form.getQuantity());
        p.setBrand(form.getBrand());
        p.setImageUrl(form.getImageUrl());
        p.setActive(form.getActive() != null ? form.getActive() : true);

        if (form.getCategoryId() == null) {
            p.setCategory(null);
        } else {
            BdhCategory cat = new BdhCategory();
            cat.setId(form.getCategoryId());
            p.setCategory(cat);
        }
    }
}
