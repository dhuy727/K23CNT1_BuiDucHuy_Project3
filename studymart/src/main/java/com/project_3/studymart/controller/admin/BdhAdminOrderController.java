package com.project_3.studymart.controller.admin;

import com.project_3.studymart.repository.BdhOrderDetailRepository;
import com.project_3.studymart.repository.BdhOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
public class BdhAdminOrderController {

    private final BdhOrderRepository orderRepo;
    private final BdhOrderDetailRepository detailRepo;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("pageTitle", "Orders");
        model.addAttribute("pageSubtitle", "Quản lý đơn hàng");
        model.addAttribute("orders", orderRepo.findAll(Sort.by(Sort.Direction.DESC, "orderDate")));
        return "admin/orders"; // templates/admin/orders.html
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, RedirectAttributes ra) {
        var orderOpt = orderRepo.findById(id);
        if (orderOpt.isEmpty()) {
            ra.addFlashAttribute("bdhMsg", "Không tìm thấy đơn hàng!");
            return "redirect:/admin/orders";
        }
        var order = orderOpt.get();

        model.addAttribute("pageTitle", "Orders");
        model.addAttribute("pageSubtitle", "Chi tiết đơn #" + id);
        model.addAttribute("order", order);
        model.addAttribute("details", detailRepo.findByOrderId(id));

        return "admin/order-detail"; // templates/admin/order-detail.html
    }

    private static final java.util.Set<String> ALLOWED =
            java.util.Set.of("PENDING","PAID","SHIPPING","DONE","CANCEL");

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam("status") String status,
                               RedirectAttributes ra) {

        var orderOpt = orderRepo.findById(id);
        if (orderOpt.isEmpty()) {
            ra.addFlashAttribute("bdhMsg", "Không tìm thấy đơn hàng!");
            return "redirect:/admin/orders";
        }

        if (!ALLOWED.contains(status)) {
            ra.addFlashAttribute("bdhMsg", "Trạng thái không hợp lệ!");
            return "redirect:/admin/orders/" + id;
        }

        var order = orderOpt.get();
        order.setStatus(status);
        orderRepo.save(order);

        ra.addFlashAttribute("bdhMsg", "Cập nhật trạng thái thành công!");
        return "redirect:/admin/orders/" + id;
    }

}
