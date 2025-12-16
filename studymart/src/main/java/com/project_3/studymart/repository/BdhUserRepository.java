package com.project_3.studymart.repository;

import com.project_3.studymart.entity.BdhUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BdhUserRepository extends JpaRepository<BdhUser, Long> {

    Optional<BdhUser> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
