package com.project_3.studymart.controller.admin;

import com.project_3.studymart.entity.BdhProduct;
import com.project_3.studymart.service.BdhProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class BdhAdminProductController {

    private final BdhProductService productService;

    @GetMapping
    public List<BdhProduct> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public BdhProduct getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @PostMapping
    public BdhProduct create(@RequestBody BdhProduct product) {
        return productService.create(product);
    }

    @PutMapping("/{id}")
    public BdhProduct update(@PathVariable Long id,
                             @RequestBody BdhProduct product) {
        return productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping("/search")
    public List<BdhProduct> search(@RequestParam("q") String keyword) {
        return productService.searchByName(keyword);
    }

    @GetMapping("/category/{categoryId}")
    public List<BdhProduct> findByCategory(@PathVariable Long categoryId) {
        return productService.findByCategory(categoryId);
    }

    @GetMapping("/brand")
    public List<BdhProduct> findByBrand(@RequestParam("name") String brand) {
        return productService.findByBrand(brand);
    }
}
