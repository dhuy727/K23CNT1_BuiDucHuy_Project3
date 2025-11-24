package k23cnt1.bdhDay06Lab.repository;

import k23cnt1.bdhDay06Lab.entity.bdhBook;
import k23cnt1.bdhDay06Lab.entity.bdhBookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface bdhBookAuthorRepository extends JpaRepository<bdhBookAuthor, Long> {

    void deleteByBdhBook(bdhBook book);
}
