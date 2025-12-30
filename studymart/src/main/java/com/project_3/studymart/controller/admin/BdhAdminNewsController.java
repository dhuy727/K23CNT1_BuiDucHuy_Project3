package com.project_3.studymart.controller.admin;

import com.project_3.studymart.entity.BdhNews;
import com.project_3.studymart.repository.BdhNewsRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/news")
public class BdhAdminNewsController {

    private final BdhNewsRepository newsRepository;

    @GetMapping
    public String bdhIndex(Model model) {
        var list = newsRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("bdhNewsList", list);
        return "admin/news";
    }

    @GetMapping("/create")
    public String bdhCreateForm(Model model) {
        model.addAttribute("bdhNews", new BdhNews());
        model.addAttribute("bdhMode", "create");
        return "admin/new-form";
    }

    @PostMapping("/create")
    public String bdhCreate(
            @Valid @ModelAttribute("bdhNews") BdhNews bdhNews,
            BindingResult bindingResult,
            RedirectAttributes ra,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bdhMode", "create");
            return "admin/new-form";
        }
        newsRepository.save(bdhNews);
        ra.addFlashAttribute("bdhMsg", "Tạo tin tức thành công!");
        return "redirect:/admin/news";
    }

    @GetMapping("/{id}/edit")
    public String bdhEditForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        var newsOpt = newsRepository.findById(id);
        if (newsOpt.isEmpty()) {
            ra.addFlashAttribute("bdhError", "Không tìm thấy tin tức!");
            return "redirect:/admin/news";
        }
        model.addAttribute("bdhNews", newsOpt.get());
        model.addAttribute("bdhMode", "edit");
        return "admin/new-form";
    }

    @PostMapping("/{id}/edit")
    public String bdhUpdate(
            @PathVariable Long id,
            @Valid @ModelAttribute("bdhNews") BdhNews bdhNews,
            BindingResult bindingResult,
            RedirectAttributes ra,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bdhMode", "edit");
            return "admin/new-form";
        }

        var currentOpt = newsRepository.findById(id);
        if (currentOpt.isEmpty()) {
            ra.addFlashAttribute("bdhError", "Không tìm thấy tin tức!");
            return "redirect:/admin/news";
        }

        bdhNews.setId(id);
        newsRepository.save(bdhNews);

        ra.addFlashAttribute("bdhMsg", "Cập nhật tin tức thành công!");
        return "redirect:/admin/news";
    }

    @PostMapping("/delete/{id}")
    public String bdhDelete(@PathVariable Long id, RedirectAttributes ra) {
        if (!newsRepository.existsById(id)) {
            ra.addFlashAttribute("bdhError", "Không tìm thấy tin tức!");
            return "redirect:/admin/news";
        }
        newsRepository.deleteById(id);
        ra.addFlashAttribute("bdhMsg", "Xóa tin tức thành công!");
        return "redirect:/admin/news";
    }
}
