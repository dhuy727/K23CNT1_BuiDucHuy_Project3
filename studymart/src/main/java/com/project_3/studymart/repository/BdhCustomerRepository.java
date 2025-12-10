package com.project_3.studymart.repository;

import com.project_3.studymart.entity.BdhCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BdhCustomerRepository extends JpaRepository<BdhCustomer, Long> {

    Optional<BdhCustomer> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
