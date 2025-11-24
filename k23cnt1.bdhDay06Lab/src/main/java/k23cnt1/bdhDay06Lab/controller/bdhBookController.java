package k23cnt1.bdhDay06Lab.controller;

import k23cnt1.bdhDay06Lab.entity.bdhAuthor;
import k23cnt1.bdhDay06Lab.entity.bdhBook;
import k23cnt1.bdhDay06Lab.entity.bdhBookAuthor;
import k23cnt1.bdhDay06Lab.repository.bdhBookAuthorRepository;
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

    @Autowired
    private bdhBookAuthorRepository bdhBookAuthorRepository;


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

    @PostMapping("/new")
    public String saveBook(@ModelAttribute("bdhBook") bdhBook book,
                           @RequestParam(value = "bdhAuthorIds", required = false) List<Long> bdhAuthorIds,
                           @RequestParam(value = "bdhEditorId", required = false) Long bdhEditorId,
                           @RequestParam(value = "imageBook", required = false) MultipartFile imageFile) {

        // ==== xử lý ảnh (giữ logic cũ của bạn) ====
        if (imageFile != null && !imageFile.isEmpty()) {
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

        // ==== đảm bảo có danh sách tác giả ====
        if (bdhAuthorIds == null || bdhAuthorIds.isEmpty()) {
            // không chọn tác giả nào, tuỳ thầy yêu cầu (có thể cho phép hoặc báo lỗi)
            book.getBdhBookAuthors().clear();
            bdhBookService.saveBook(book);
            return "redirect:/bdhbooks";
        }

        // nếu bdhEditorId null hoặc không nằm trong list tác giả -> mặc định người đầu tiên là chủ biên
        if (bdhEditorId == null || !bdhAuthorIds.contains(bdhEditorId)) {
            bdhEditorId = bdhAuthorIds.get(0);
        }

        // Xóa các quan hệ cũ (khi edit)
        book.getBdhBookAuthors().clear();

        List<bdhBookAuthor> relations = new ArrayList<>();
        for (Long authorId : bdhAuthorIds) {
            bdhAuthor author = bdhAuthorService.getAuthorsById(authorId);
            if (author == null) continue;

            bdhBookAuthor relation = new bdhBookAuthor();
            relation.setBdhBook(book);
            relation.setBdhAuthor(author);
            relation.setBdhIsEditor(authorId.equals(bdhEditorId)); // true nếu là chủ biên

            relations.add(relation);
        }

        book.getBdhBookAuthors().addAll(relations);

        // nhờ cascade = ALL, Hibernate sẽ lưu cả bdhBookAuthor
        bdhBookService.saveBook(book);

        return "redirect:/bdhbooks";
    }



    // FORM EDIT
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        bdhBook book = bdhBookService.getBookById(id);
        model.addAttribute("bdhBook", book);
        model.addAttribute("bdhAuthors", bdhAuthorService.getAllAuthors());
        return "bdhbooks/bdh-book-form";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bdhBookService.deleteBook(id);
        return "redirect:/bdhbooks";
    }
}

