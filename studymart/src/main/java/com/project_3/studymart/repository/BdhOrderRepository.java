package com.project_3.studymart.repository;

import com.project_3.studymart.entity.BdhOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BdhOrderRepository extends JpaRepository<BdhOrder, Long> {

    List<BdhOrder> findByUser_Id(Long userId);
    List<BdhOrder> findByUserIdOrderByOrderDateDesc(Long userId);

}
