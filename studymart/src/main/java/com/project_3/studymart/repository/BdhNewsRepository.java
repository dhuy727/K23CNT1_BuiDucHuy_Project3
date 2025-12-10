package com.project_3.studymart.repository;

import com.project_3.studymart.entity.BdhNews;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BdhNewsRepository extends JpaRepository<BdhNews, Long> {

    List<BdhNews> findByActiveTrueOrderByCreatedAtDesc();

}
