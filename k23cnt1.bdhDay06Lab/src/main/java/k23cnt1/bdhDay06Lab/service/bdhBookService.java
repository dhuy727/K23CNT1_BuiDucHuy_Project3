package k23cnt1.bdhDay06Lab.service;

import k23cnt1.bdhDay06Lab.entity.bdhBook;
import k23cnt1.bdhDay06Lab.repository.bdhBookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class bdhBookService {
    @Autowired
    private bdhBookRepository bdhBookRepository;
    public List<bdhBook> getAllBooks() {
        return bdhBookRepository.findAll();
    }
    public bdhBook saveBook(bdhBook book) {
        return bdhBookRepository.save(book);
    }
    public bdhBook getBookById(Long id) {
        return bdhBookRepository.findById(id).orElse(null);
    }
    public void deleteBook(Long id) {
        bdhBookRepository.deleteById(id);
    }

}
