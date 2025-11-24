package k23cnt1.bdhDay06Lab.controller;

import k23cnt1.bdhDay06Lab.entity.bdhAuthor;
import k23cnt1.bdhDay06Lab.service.bdhAuthorService;
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

@Controller
@RequestMapping("/bdhauthors")
public class bdhAuthorController {

    @Autowired
    private bdhAuthorService bdhAuthorService;

    // thư mục lưu ảnh (giống book controller nhưng tách folder authors)
    private static final String UPLOAD_DIR = "src/main/resources/static/";
    private static final String UPLOAD_AUTHOR_PATH = "images/authors/";

    // LIST
    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("bdhAuthors", bdhAuthorService.getAllAuthors());
        return "bdhauthors/bdh-author-list";
    }

    // FORM CREATE
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("bdhAuthor", new bdhAuthor());
        return "bdhauthors/bdh-author-form";
    }

    // SAVE (CREATE/UPDATE)
    @PostMapping("/new")
    public String saveAuthor(@ModelAttribute("bdhAuthor") bdhAuthor author,
                             @RequestParam(value = "imageAuthor", required = false) MultipartFile imageFile) {

        // upload ảnh nếu có
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR + UPLOAD_AUTHOR_PATH);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String originalFilename = StringUtils.cleanPath(imageFile.getOriginalFilename());
                String fileExtension = "";
                int dotIndex = originalFilename.lastIndexOf(".");
                if (dotIndex >= 0) {
                    fileExtension = originalFilename.substring(dotIndex);
                }

                // đặt tên file theo mã tác giả
                String newFileName = author.getBdhCode() + fileExtension;

                Path filePath = uploadPath.resolve(newFileName);
                Files.copy(imageFile.getInputStream(), filePath);

                author.setBdhImgUrl("/" + UPLOAD_AUTHOR_PATH + newFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        bdhAuthorService.saveAuthors(author);
        return "redirect:/bdhauthors";
    }

    // FORM EDIT
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        bdhAuthor author = bdhAuthorService.getAuthorsById(id);
        model.addAttribute("bdhAuthor", author);
        return "bdhauthors/bdh-author-form";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        bdhAuthorService.deleteAuthors(id);
        return "redirect:/bdhauthors";
    }
}
