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

    @GetMapping
    public ResponseEntity<List<BdhPaymentMethod>> getAll() {
        return ResponseEntity.ok(paymentMethodService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BdhPaymentMethod> getById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentMethodService.getById(id));
    }

    @PostMapping
    public ResponseEntity<BdhPaymentMethod> create(@RequestBody BdhPaymentMethod request) {
        request.setId(null);
        BdhPaymentMethod saved = paymentMethodService.create(request);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BdhPaymentMethod> update(@PathVariable Long id,
                                                   @RequestBody BdhPaymentMethod request) {
        BdhPaymentMethod updated = paymentMethodService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentMethodService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
