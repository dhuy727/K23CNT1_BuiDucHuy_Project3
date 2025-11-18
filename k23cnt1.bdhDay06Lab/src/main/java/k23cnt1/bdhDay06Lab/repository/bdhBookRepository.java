package k23cnt1.bdhDay06Lab.repository;

import k23cnt1.bdhDay06Lab.entity.bdhBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface bdhBookRepository extends JpaRepository<bdhBook, Long> {
}
