package com.bdh.labguide04.service;

import com.bdh.labguide04.dto.EmployeeDTO;
import com.bdh.labguide04.entity.Employee;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private final List<Employee> employees = new ArrayList<>(List.of(
            new Employee(1L, "Nguyễn Văn A", "Nam", 25, 15000000),
            new Employee(2L, "Trần Thị B", "Nữ", 30, 20000000),
            new Employee(3L, "Lê Văn C", "Nam", 28, 18000000)
    ));

    public List<Employee> getAll() { return employees; }
    public Employee getById(Long id) { return employees.stream().filter(e -> e.id().equals(id)).findFirst().orElse(null); }

    public Employee add(EmployeeDTO dto) {
        Long newId = employees.stream().mapToLong(Employee::id).max().orElse(0L) + 1;
        Employee emp = new Employee(newId, dto.fullName(), dto.gender(), dto.age(), dto.salary());
        employees.add(emp);
        return emp;
    }

    public Employee update(Long id, EmployeeDTO dto) {
        Employee e = getById(id);
        if (e == null) return null;
        employees.removeIf(item -> item.id().equals(id));
        Employee updated = new Employee(id, dto.fullName(), dto.gender(), dto.age(), dto.salary());
        employees.add(updated);
        return updated;
    }

    public boolean delete(Long id) { return employees.removeIf(e -> e.id().equals(id)); }
}