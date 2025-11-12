package com.bdh.labguide04.controller;

import com.bdh.labguide04.dto.KhoaDTO;
import com.bdh.labguide04.entity.Khoa;
import com.bdh.labguide04.service.KhoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/khoa")
public class KhoaController {
    @Autowired private KhoaService service;
    @GetMapping("") public List<Khoa> getAll() { return service.getAll(); }
    @GetMapping("/{makh}") public Khoa getByMa(@PathVariable String makh) { return service.getByMa(makh); }
    @PostMapping("") public Khoa add(@Valid @RequestBody KhoaDTO dto) { return service.add(dto); }
    @PutMapping("/{makh}") public Khoa update(@PathVariable String makh, @Valid @RequestBody KhoaDTO dto) { return service.update(makh, dto); }
    @DeleteMapping("/{makh}") public boolean delete(@PathVariable String makh) { return service.delete(makh); }
}