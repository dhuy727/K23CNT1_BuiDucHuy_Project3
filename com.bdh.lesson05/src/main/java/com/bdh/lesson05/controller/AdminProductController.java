package com.bdh.lesson05.controller;

import com.bdh.lesson05.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    // Dữ liệu giả để demo (sau này bạn thay bằng DB)
    private List<Product> fakeProducts() {
        return Arrays.asList(
                new Product(1L, "Sản phẩm A", 100000.0, 10),
                new Product(2L, "Sản phẩm B", 150000.0, 5),
                new Product(3L, "Sản phẩm C", 200000.0, 8)
        );
    }

    // Trang danh sách (table)
    @GetMapping
    public String list(Model model) {
        model.addAttribute("title", "Quản lý sản phẩm");
        model.addAttribute("products", fakeProducts());
        return "admin/product-list";
    }

    // Form thêm mới
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("title", "Thêm sản phẩm");
        model.addAttribute("product", new Product());
        return "admin/product-form-add";
    }

    // Form sửa (demo lấy 1 bản ghi mẫu)
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Product p = new Product(id, "Sản phẩm Demo", 123456.0, 9);
        model.addAttribute("title", "Sửa sản phẩm");
        model.addAttribute("product", p);
        return "admin/product-form-edit";
    }

    // Các @PostMapping xử lý lưu/xóa bạn có thể tự bổ sung sau (lab yêu cầu chủ yếu là thiết kế trang)
}
