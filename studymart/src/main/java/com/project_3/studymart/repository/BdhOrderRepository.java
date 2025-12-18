package com.project_3.studymart.repository;

import com.project_3.studymart.entity.BdhOrder;
import com.project_3.studymart.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BdhOrderRepository extends JpaRepository<BdhOrder, Long> {

    long countByStatus(OrderStatus status);

    @Query("select coalesce(sum(o.totalPrice), sum(o.totalAmount), 0) from BdhOrder o")
    Double sumRevenue();

    List<BdhOrder> findByUser_Id(Long userId);
    List<BdhOrder> findByUserIdOrderByOrderDateDesc(Long userId);
    List<BdhOrder> findTop5ByOrderByOrderDateDesc();
    boolean existsByUser_Id(Long userId);
    long countByUser_Id(Long userId);
}
