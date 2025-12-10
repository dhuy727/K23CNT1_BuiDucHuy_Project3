package com.project_3.studymart.repository;

import com.project_3.studymart.entity.BdhCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BdhCategoryRepository extends JpaRepository<BdhCategory, Long> {
    List<BdhCategory> findByNameContainingIgnoreCase(String name);
}

