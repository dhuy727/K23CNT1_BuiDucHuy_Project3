package com.project_3.studymart.controller.user;

import com.project_3.studymart.dto.BdhCreateOrderRequest;
import com.project_3.studymart.entity.BdhOrder;
import com.project_3.studymart.service.BdhOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class BdhOrderClientController {

    private final BdhOrderService orderService;

    // Đặt hàng
    @PostMapping
    public ResponseEntity<BdhOrder> createOrder(@RequestBody BdhCreateOrderRequest req,
                                                Authentication authentication) {
        String username = authentication.getName();
        BdhOrder order = orderService.createOrder(username, req);
        return ResponseEntity.ok(order);
    }

    // Xem đơn của chính mình
    @GetMapping("/my")
    public ResponseEntity<List<BdhOrder>> myOrders(Authentication authentication) {
        String username = authentication.getName();
        List<BdhOrder> orders = orderService.getOrdersOfCustomer(username);
        return ResponseEntity.ok(orders);
    }
}
