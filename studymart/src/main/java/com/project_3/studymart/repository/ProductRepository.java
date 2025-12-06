package com.project_3.studymart.repository;

import com.project_3.studymart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategory_Id(Long categoryId);

    List<Product> findByBrandContainingIgnoreCase(String brand);

    List<Product> findByActiveTrue();
}
