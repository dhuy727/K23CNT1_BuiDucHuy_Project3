package com.project_3.studymart.controller.admin;

import com.project_3.studymart.entity.BdhOrder;
import com.project_3.studymart.service.BdhOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class BdhAdminOrderController {

    private final BdhOrderService orderService;

    @GetMapping
    public ResponseEntity<List<BdhOrder>> getAll() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BdhOrder> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<BdhOrder> updateStatus(@PathVariable Long id,
                                                 @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateStatus(id, status));
    }
}
