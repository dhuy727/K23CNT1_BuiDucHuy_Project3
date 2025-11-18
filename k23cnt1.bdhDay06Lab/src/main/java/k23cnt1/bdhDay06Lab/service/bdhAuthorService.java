package k23cnt1.bdhDay06Lab.service;

import k23cnt1.bdhDay06Lab.entity.bdhAuthor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import k23cnt1.bdhDay06Lab.repository.bdhAuthorRepository;
import java.util.List;
@Service

public class bdhAuthorService {
    @Autowired
    private bdhAuthorRepository bdhAuthorRepository;
    public List<bdhAuthor> getAllAuthors() {
        return bdhAuthorRepository.findAll();
    }
    public bdhAuthor saveAuthors(bdhAuthor author) {
        return bdhAuthorRepository.save(author);
    }
    public bdhAuthor getAuthorsById(Long id) {
        return bdhAuthorRepository.findById(id).orElse(null);
    }
    public void deleteAuthors(Long id) {
        bdhAuthorRepository.deleteById(id);
    }
    public List<bdhAuthor> findAllById(List<Long> ids) {
        return bdhAuthorRepository.findAllById(ids);
    }
}
