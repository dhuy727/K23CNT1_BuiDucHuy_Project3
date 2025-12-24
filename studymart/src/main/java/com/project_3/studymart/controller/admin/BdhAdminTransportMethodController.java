package com.project_3.studymart.controller.admin;

import com.project_3.studymart.entity.BdhTransportMethod;
import com.project_3.studymart.service.BdhTransportMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/transport-methods")
@RequiredArgsConstructor
public class BdhAdminTransportMethodController {

    private final BdhTransportMethodService transportService;

    @GetMapping
    public ResponseEntity<List<BdhTransportMethod>> getAll() {
        return ResponseEntity.ok(transportService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BdhTransportMethod> getById(@PathVariable Long id) {
        return ResponseEntity.ok(transportService.getById(id));
    }

    @PostMapping
    public ResponseEntity<BdhTransportMethod> create(@RequestBody BdhTransportMethod request) {
        request.setId(null); // đảm bảo luôn create
        return ResponseEntity.ok(transportService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BdhTransportMethod> update(@PathVariable Long id,
                                                     @RequestBody BdhTransportMethod request) {
        return ResponseEntity.ok(transportService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transportService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
