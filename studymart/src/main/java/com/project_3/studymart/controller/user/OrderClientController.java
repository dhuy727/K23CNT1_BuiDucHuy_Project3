package com.project_3.studymart.controller.user;

import com.project_3.studymart.dto.CreateOrderRequest;
import com.project_3.studymart.entity.Order;
import com.project_3.studymart.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderClientController {

    private final OrderService orderService;

    // Đặt hàng
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest req,
                                             Authentication authentication) {
        String username = authentication.getName();
        Order order = orderService.createOrder(username, req);
        return ResponseEntity.ok(order);
    }

    // Xem đơn của chính mình
    @GetMapping("/my")
    public ResponseEntity<List<Order>> myOrders(Authentication authentication) {
        String username = authentication.getName();
        List<Order> orders = orderService.getOrdersOfCustomer(username);
        return ResponseEntity.ok(orders);
    }
}
