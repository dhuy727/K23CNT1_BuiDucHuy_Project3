package com.project_3.studymart.repository;

import com.project_3.studymart.entity.BdhOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BdhOrderDetailRepository extends JpaRepository<BdhOrderDetail, Long> {
    List<BdhOrderDetail> findByOrderId(Long orderId);
}
