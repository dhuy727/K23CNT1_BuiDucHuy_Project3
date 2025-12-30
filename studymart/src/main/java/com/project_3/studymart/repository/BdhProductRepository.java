package com.project_3.studymart.repository;

import com.project_3.studymart.entity.BdhProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BdhProductRepository extends JpaRepository<BdhProduct, Long> {

    List<BdhProduct> findByNameContainingIgnoreCase(String name);

    List<BdhProduct> findByCategory_Id(Long categoryId);

    List<BdhProduct> findByBrandContainingIgnoreCase(String brand);

    List<BdhProduct> findByActiveTrue();

    Page<BdhProduct> findByNameContainingIgnoreCaseOrBrandContainingIgnoreCase(
            String name, String brand, Pageable pageable
    );
    List<BdhProduct> findByNameContainingIgnoreCaseOrBrandContainingIgnoreCase(
            String name,
            String brand
    );
    List<BdhProduct> findTop6ByActiveTrueOrderByIdDesc();

}
