package com.bdh.labguide04.service;

import com.bdh.labguide04.dto.KhoaDTO;
import com.bdh.labguide04.entity.Khoa;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class KhoaService {
    private final List<Khoa> khoas = new ArrayList<>(List.of(
            new Khoa("CNTT", "Công nghệ thông tin"),
            new Khoa("KT", "Kế toán"),
            new Khoa("QTKD", "Quản trị kinh doanh"),
            new Khoa("NN", "Ngoại ngữ"),
            new Khoa("SP", "Sư phạm")
    ));

    public List<Khoa> getAll() { return khoas; }
    public Khoa getByMa(String makh) { return khoas.stream().filter(k -> k.makh().equals(makh)).findFirst().orElse(null); }
    public Khoa add(KhoaDTO dto) {
        Khoa k = new Khoa(dto.makh(), dto.tenkh());
        khoas.add(k);
        return k;
    }
    public Khoa update(String makh, KhoaDTO dto) {
        Khoa k = getByMa(makh);
        if (k == null) return null;
        khoas.removeIf(item -> item.makh().equals(makh));
        Khoa updated = new Khoa(dto.makh(), dto.tenkh());
        khoas.add(updated);
        return updated;
    }
    public boolean delete(String makh) { return khoas.removeIf(k -> k.makh().equals(makh)); }
}