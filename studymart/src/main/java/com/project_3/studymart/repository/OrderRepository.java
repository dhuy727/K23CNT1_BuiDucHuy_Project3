package com.project_3.studymart.repository;

import com.project_3.studymart.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // lấy đơn theo customer
    List<Order> findByCustomer_Id(Long customerId);

}
