package com.bdh.labguide04.service;

import com.bdh.labguide04.dto.MonHocDTO;
import com.bdh.labguide04.entity.MonHoc;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MonHocService {
    private final List<MonHoc> monhocs = new ArrayList<>(List.of(
            new MonHoc("JAVA", "Lập trình Java", 60),
            new MonHoc("WEB", "Thiết kế Web", 45),
            new MonHoc("DB", "Cơ sở dữ liệu", 50),
            new MonHoc("AI", "Trí tuệ nhân tạo", 70),
            new MonHoc("MOB", "Lập trình Mobile", 65)
    ));

    public List<MonHoc> getAll() { return monhocs; }
    public MonHoc getByMa(String mamh) { return monhocs.stream().filter(m -> m.mamh().equals(mamh)).findFirst().orElse(null); }
    public MonHoc add(MonHocDTO dto) { MonHoc mh = new MonHoc(dto.mamh(), dto.tenmh(), dto.sotiet()); monhocs.add(mh); return mh; }
    public MonHoc update(String mamh, MonHocDTO dto) {
        MonHoc mh = getByMa(mamh);
        if (mh == null) return null;
        monhocs.removeIf(item -> item.mamh().equals(mamh));
        MonHoc updated = new MonHoc(dto.mamh(), dto.tenmh(), dto.sotiet());
        monhocs.add(updated);
        return updated;
    }
    public boolean delete(String mamh) { return monhocs.removeIf(m -> m.mamh().equals(mamh)); }
}