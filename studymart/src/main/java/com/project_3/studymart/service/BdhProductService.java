package com.project_3.studymart.service;

import com.project_3.studymart.entity.BdhCategory;
import com.project_3.studymart.entity.BdhProduct;
import com.project_3.studymart.repository.BdhProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BdhProductService {

    private final BdhProductRepository repo;
    private final BdhCategoryService categoryService;

    public List<BdhProduct> getAll() {
        return repo.findAll();
    }

    public List<BdhProduct> getAllActive() {
        return repo.findByActiveTrue();
    }

    public BdhProduct getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // tạo mới sản phẩm (nhận vào entity có category.id)
    public BdhProduct create(BdhProduct product) {
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            BdhCategory cat = categoryService.getById(product.getCategory().getId());
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

    public BdhProduct update(Long id, BdhProduct product) {
        BdhProduct old = getById(id);

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
            BdhCategory cat = categoryService.getById(product.getCategory().getId());
            old.setCategory(cat);
        }

        return repo.save(old);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<BdhProduct> searchByName(String keyword) {
        return repo.findByNameContainingIgnoreCase(keyword);
    }

    public List<BdhProduct> findByCategory(Long categoryId) {
        return repo.findByCategory_Id(categoryId);
    }

    public List<BdhProduct> findByBrand(String brand) {
        return repo.findByBrandContainingIgnoreCase(brand);
    }
}
