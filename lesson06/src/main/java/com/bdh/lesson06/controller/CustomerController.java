package com.bdh.lesson06.controller;

import com.bdh.lesson06.dto.CustomerDTO;
import com.bdh.lesson06.entity.Customer;
import com.bdh.lesson06.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Danh sách
    @GetMapping
    public String list(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customers/customer-list";
    }

    // Form thêm
    @GetMapping("/add-new")
    public String addNew(Model model) {
        model.addAttribute("customer", new CustomerDTO());
        return "customers/customer-add";
    }

    // Form sửa
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        CustomerDTO dto = customerService.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid customer Id:" + id));
        model.addAttribute("customer", dto);
        return "customers/customer-edit";
    }

    // Lưu thêm mới
    @PostMapping
    public String save(@ModelAttribute("customer") CustomerDTO dto) {
        customerService.save(dto);
        return "redirect:/customers";
    }

    // Lưu cập nhật
    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute("customer") CustomerDTO dto) {
        customerService.updateCustomer(id, dto);
        return "redirect:/customers";
    }

    // Xoá
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }
}
