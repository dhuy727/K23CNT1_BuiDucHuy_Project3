package com.project_3.studymart.service;

import com.project_3.studymart.entity.Category;
import com.project_3.studymart.entity.Product;
import com.project_3.studymart.repository.ProductRepository;
import com.project_3.studymart.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repo;
    private final CategoryService categoryService;

    public List<Product> getAll() {
        return repo.findAll();
    }

    public List<Product> getAllActive() {
        return repo.findByActiveTrue();
    }

    public Product getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // tạo mới sản phẩm (nhận vào entity có category.id)
    public Product create(Product product) {
        // đảm bảo category tồn tại
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Category cat = categoryService.getById(product.getCategory().getId());
            product.setCategory(cat);
        } else {
            throw new RuntimeException("Category is required");
        }

        product.setId(null);
        if (product.getActive() == null) {
            product.setActive(true);
        }
        return repo.save(product);
    }

    public Product update(Long id, Product product) {
        Product old = getById(id);

        old.setName(product.getName());
        old.setDescription(product.getDescription());
        old.setPrice(product.getPrice());
        old.setQuantity(product.getQuantity());
        old.setBrand(product.getBrand());
        old.setImageUrl(product.getImageUrl());

        if (product.getActive() != null) {
            old.setActive(product.getActive());
        }

        // cập nhật lại category nếu gửi lên
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Category cat = categoryService.getById(product.getCategory().getId());
            old.setCategory(cat);
        }

        return repo.save(old);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<Product> searchByName(String keyword) {
        return repo.findByNameContainingIgnoreCase(keyword);
    }

    public List<Product> findByCategory(Long categoryId) {
        return repo.findByCategory_Id(categoryId);
    }

    public List<Product> findByBrand(String brand) {
        return repo.findByBrandContainingIgnoreCase(brand);
    }
}
