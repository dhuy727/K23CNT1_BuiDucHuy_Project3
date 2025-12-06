package com.project_3.studymart.controller.user;

import com.project_3.studymart.entity.Product;
import com.project_3.studymart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductClientController {

    private final ProductService productService;

    // lấy danh sách sản phẩm đang bán
    @GetMapping
    public List<Product> getAllActive() {
        return productService.getAllActive();
    }

    @GetMapping("/{id}")
    public Product getDetail(@PathVariable Long id) {
        return productService.getById(id);
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam("q") String keyword) {
        return productService.searchByName(keyword);
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> findByCategory(@PathVariable Long categoryId) {
        return productService.findByCategory(categoryId);
    }
}
