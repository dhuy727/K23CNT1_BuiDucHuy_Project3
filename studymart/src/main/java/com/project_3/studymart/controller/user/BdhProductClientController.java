package com.project_3.studymart.controller.user;

import com.project_3.studymart.entity.BdhProduct;
import com.project_3.studymart.service.BdhProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class BdhProductClientController {

    private final BdhProductService productService;

    // lấy danh sách sản phẩm đang bán
    @GetMapping
    public List<BdhProduct> getAllActive() {
        return productService.getAllActive();
    }

    @GetMapping("/{id}")
    public BdhProduct getDetail(@PathVariable Long id) {
        return productService.getById(id);
    }

    @GetMapping("/search")
    public List<BdhProduct> search(@RequestParam("q") String keyword) {
        return productService.searchByName(keyword);
    }

    @GetMapping("/category/{categoryId}")
    public List<BdhProduct> findByCategory(@PathVariable Long categoryId) {
        return productService.findByCategory(categoryId);
    }
}
