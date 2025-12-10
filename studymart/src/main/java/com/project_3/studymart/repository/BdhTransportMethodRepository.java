package com.project_3.studymart.repository;

import com.project_3.studymart.entity.BdhTransportMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BdhTransportMethodRepository extends JpaRepository<BdhTransportMethod, Long> {
    List<BdhTransportMethod> findByActiveTrue();
}