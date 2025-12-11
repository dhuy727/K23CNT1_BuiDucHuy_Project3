package com.project_3.studymart.controller.admin;

import com.project_3.studymart.entity.BdhPaymentMethod;
import com.project_3.studymart.service.BdhPaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/payment-methods")
@RequiredArgsConstructor
public class BdhAdminPaymentMethodController {

    private final BdhPaymentMethodService paymentMethodService;

    // Lấy tất cả phương thức thanh toán (kể cả inactive)
    @GetMapping
    public ResponseEntity<List<BdhPaymentMethod>> getAll() {
        return ResponseEntity.ok(paymentMethodService.getAll());
    }

    // Lấy chi tiết 1 phương thức thanh toán
    @GetMapping("/{id}")
    public ResponseEntity<BdhPaymentMethod> getById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentMethodService.getById(id));
    }

    // POST /api/admin/payment-methods
    @PostMapping
    public ResponseEntity<BdhPaymentMethod> create(@RequestBody BdhPaymentMethod request) {
        // id để null để đảm bảo tạo mới
        request.setId(null);
        BdhPaymentMethod saved = paymentMethodService.create(request);
        return ResponseEntity.ok(saved);
    }

    // PUT /api/admin/payment-methods/{id}
    @PutMapping("/{id}")
    public ResponseEntity<BdhPaymentMethod> update(@PathVariable Long id,
                                                   @RequestBody BdhPaymentMethod request) {
        BdhPaymentMethod updated = paymentMethodService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/admin/payment-methods/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentMethodService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
