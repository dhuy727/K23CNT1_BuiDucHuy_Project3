package com.project_3.studymart.controller.admin;

import com.project_3.studymart.enums.OrderStatus;
import com.project_3.studymart.repository.BdhOrderDetailRepository;
import com.project_3.studymart.repository.BdhOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
        return "admin/orders";
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

        OrderStatus current = order.getStatus();
        List<OrderStatus> allowed = switch (current) {
            case PENDING  -> List.of(OrderStatus.PENDING, OrderStatus.PAID, OrderStatus.CANCEL);
            case PAID     -> List.of(OrderStatus.PAID, OrderStatus.SHIPPING);
            case SHIPPING -> List.of(OrderStatus.SHIPPING, OrderStatus.DONE);
            case DONE     -> List.of(OrderStatus.DONE);
            case CANCEL   -> List.of(OrderStatus.CANCEL);
            case NEW      -> List.of(OrderStatus.NEW, OrderStatus.PENDING, OrderStatus.CANCEL);

        };
        model.addAttribute("allowedStatuses", allowed);

        return "admin/order-detail";
    }

    private boolean isValidTransition(OrderStatus from, OrderStatus to) {
        return switch (from) {
            case NEW -> (to == OrderStatus.PENDING || to == OrderStatus.CANCEL);

            case PENDING -> (to == OrderStatus.PAID || to == OrderStatus.CANCEL);
            case PAID -> (to == OrderStatus.SHIPPING);
            case SHIPPING -> (to == OrderStatus.DONE);

            default -> false;
        };
    }

    @PostMapping("/{id}/status")
    @Transactional
    public String updateStatus(@PathVariable Long id,
                               @RequestParam("status") OrderStatus newStatus,
                               RedirectAttributes ra) {

        var order = orderRepo.findById(id).orElse(null);
        if (order == null) {
            ra.addFlashAttribute("bdhMsg", "Không tìm thấy đơn hàng!");
            return "redirect:/admin/orders";
        }

        OrderStatus current = order.getStatus();

        if (current == OrderStatus.DONE || current == OrderStatus.CANCEL) {
            ra.addFlashAttribute("bdhMsg", "Đơn hàng đã kết thúc, không thể thay đổi!");
            return "redirect:/admin/orders/" + id;
        }

        if (!isValidTransition(current, newStatus)) {
            ra.addFlashAttribute("bdhMsg", "Chuyển trạng thái không hợp lệ!");
            return "redirect:/admin/orders/" + id;
        }

        if (newStatus == OrderStatus.DONE) {
            for (var d : order.getDetails()) {
                var product = d.getProduct();
                int remain = product.getQuantity() - d.getQuantity();
                if (remain < 0) {
                    ra.addFlashAttribute("bdhMsg", "Không đủ tồn kho: " + product.getName());
                    return "redirect:/admin/orders/" + id;
                }
                product.setQuantity(remain);
            }
        }

        order.setStatus(newStatus);
        orderRepo.save(order);

        ra.addFlashAttribute("bdhMsg", "Cập nhật trạng thái thành công!");
        return "redirect:/admin/orders/" + id;
    }

}
