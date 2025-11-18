package k23cnt1.bdhDay06Lab.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class bdhAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bdhId;
    private String bdhCode;
    private String bdhName;
    private String bdhDescription;
    private String bdhImgUrl;
    private String bdhEmail;
    private String bdhPhone;
    private String bdhAddress;
    private boolean bdhIsActive;
    // Tạo mối quan hệ với bảng book
    @ManyToMany(mappedBy = "bdhAuthor")
    private List<bdhBook> books = new ArrayList<>();
}

