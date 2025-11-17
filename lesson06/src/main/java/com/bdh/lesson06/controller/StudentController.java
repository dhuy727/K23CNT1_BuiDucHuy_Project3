package com.bdh.lesson06.controller;

import com.bdh.lesson06.dto.StudentDTO;
import com.bdh.lesson06.entity.Student;
import com.bdh.lesson06.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Danh sách
    @GetMapping
    public String getStudents(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "students/student-list";
    }

    // Form thêm mới
    @GetMapping("/add-new")
    public String addNewStudent(Model model) {
        model.addAttribute("student", new StudentDTO());
        return "students/student-add";
    }

    // Form sửa
    @GetMapping("/edit/{id}")
    public String showFormForUpdate(@PathVariable("id") Long id,
                                    Model model) {
        StudentDTO student = studentService.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid student Id:" + id));
        model.addAttribute("student", student);
        return "students/student-edit";
    }

    // Lưu thêm mới
    @PostMapping
    public String saveStudent(@ModelAttribute("student") StudentDTO student) {
        studentService.save(student);
        return "redirect:/students";
    }

    // Lưu cập nhật
    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable("id") Long id,
                                @ModelAttribute("student") StudentDTO student) {
        studentService.updateStudentById(id, student);
        return "redirect:/students";
    }

    // Xoá
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}
