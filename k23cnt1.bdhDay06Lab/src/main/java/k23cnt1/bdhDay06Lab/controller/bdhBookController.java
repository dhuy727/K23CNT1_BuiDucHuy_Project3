package k23cnt1.bdhDay06Lab.controller;

import k23cnt1.bdhDay06Lab.entity.bdhAuthor;
import k23cnt1.bdhDay06Lab.entity.bdhBook;
import k23cnt1.bdhDay06Lab.service.bdhAuthorService;
import k23cnt1.bdhDay06Lab.service.bdhBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/bdhbooks")
public class bdhBookController {

    @Autowired
    private bdhBookService bdhBookService;

    @Autowired
    private bdhAuthorService bdhAuthorService;

    private static final String UPLOAD_DIR = "src/main/resources/static/";
    private static final String UPLOAD_PathFile = "images/products/";

    // LIST
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("bdhBooks", bdhBookService.getAllBooks());
        return "bdhbooks/bdh-book-list";
    }

    // FORM CREATE
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("bdhBook", new bdhBook());
        model.addAttribute("bdhAuthors", bdhAuthorService.getAllAuthors());
        return "bdhbooks/bdh-book-form";
    }

    // SAVE (CREATE/UPDATE)
    @PostMapping("/new")
    public String saveBook(@ModelAttribute("bdhBook") bdhBook book,
                           @RequestParam("bdhAuthorIds") List<Long> bdhAuthorIds,
                           @RequestParam("imageBook") MultipartFile imageFile) {

        // upload ảnh
        if (!imageFile.isEmpty()) {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR + UPLOAD_PathFile);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String originalFilename = StringUtils.cleanPath(imageFile.getOriginalFilename());
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFileName = book.getBdhCode() + fileExtension;

                Path filePath = uploadPath.resolve(newFileName);
                Files.copy(imageFile.getInputStream(), filePath);

                book.setBdhImgUrl("/" + UPLOAD_PathFile + newFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // gán authors
        List<bdhAuthor> authors = new ArrayList<>();
        if (bdhAuthorIds == null) {
            bdhAuthorIds = new ArrayList<>();
        }
        book.setBdhAuthor(authors);

        bdhBookService.saveBook(book);
        return "redirect:/bdhbooks";
    }

    // FORM EDIT
    @GetMapping("/bdhedit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        bdhBook book = bdhBookService.getBookById(id);
        model.addAttribute("bdhBook", book);
        model.addAttribute("bdhAuthors", bdhAuthorService.getAllAuthors());
        return "bdhbooks/bdh-book-form";
    }

    // DELETE
    @GetMapping("/bdhdelete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bdhBookService.deleteBook(id);
        return "redirect:/bdhbooks";
    }
}

