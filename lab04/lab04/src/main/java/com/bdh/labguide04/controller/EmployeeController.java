package com.bdh.labguide04.controller;

import com.bdh.labguide04.dto.EmployeeDTO;
import com.bdh.labguide04.entity.Employee;
import com.bdh.labguide04.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired private EmployeeService service;
    @GetMapping("") public List<Employee> getAll() { return service.getAll(); }
    @GetMapping("/{id}") public Employee getById(@PathVariable Long id) { return service.getById(id); }
    @PostMapping("") public Employee add(@Valid @RequestBody EmployeeDTO dto) { return service.add(dto); }
    @PutMapping("/{id}") public Employee update(@PathVariable Long id, @Valid @RequestBody EmployeeDTO dto) { return service.update(id, dto); }
    @DeleteMapping("/{id}") public boolean delete(@PathVariable Long id) { return service.delete(id); }
}