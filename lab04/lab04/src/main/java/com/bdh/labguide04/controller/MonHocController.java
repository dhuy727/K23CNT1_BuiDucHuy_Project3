package com.bdh.labguide04.controller;

import com.bdh.labguide04.dto.MonHocDTO;
import com.bdh.labguide04.entity.MonHoc;
import com.bdh.labguide04.service.MonHocService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/monhoc")
public class MonHocController {
    @Autowired private MonHocService service;
    @GetMapping("") public List<MonHoc> getAll() { return service.getAll(); }
    @GetMapping("/{mamh}") public MonHoc getByMa(@PathVariable String mamh) { return service.getByMa(mamh); }
    @PostMapping("") public MonHoc add(@Valid @RequestBody MonHocDTO dto) { return service.add(dto); }
    @PutMapping("/{mamh}") public MonHoc update(@PathVariable String mamh, @Valid @RequestBody MonHocDTO dto) { return service.update(mamh, dto); }
    @DeleteMapping("/{mamh}") public boolean delete(@PathVariable String mamh) { return service.delete(mamh); }
}