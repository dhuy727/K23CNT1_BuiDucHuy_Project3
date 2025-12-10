package com.project_3.studymart.repository;

import com.project_3.studymart.entity.BdhPaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BdhPaymentMethodRepository extends JpaRepository<BdhPaymentMethod, Long> {
    List<BdhPaymentMethod> findByActiveTrue();
}

