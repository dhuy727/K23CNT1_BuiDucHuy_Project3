package k23cnt1.bdhDay06Lab.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class bdhBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long bdhId;

    String bdhCode;
    String bdhName;
    String bdhDescription;
    String bdhImgUrl;
    Integer bdhQuantity;
    Double bdhPrice;
    Boolean bdhIsActive;

    // ===== Quan hệ với bảng trung gian bdh_book_author =====
    @OneToMany(
            mappedBy = "bdhBook",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    List<bdhBookAuthor> bdhBookAuthors = new ArrayList<>();

    // ===== Helper cho Thymeleaf: kiểm tra book có author này chưa =====
    public boolean hasAuthor(Long authorId) {
        if (bdhBookAuthors == null) return false;
        return bdhBookAuthors.stream()
                .anyMatch(ba -> ba.getBdhAuthor() != null
                        && ba.getBdhAuthor().getBdhId().equals(authorId));
    }

    // ===== Helper: lấy id chủ biên =====
    public Long getEditorId() {
        if (bdhBookAuthors == null) return null;
        return bdhBookAuthors.stream()
                .filter(ba -> Boolean.TRUE.equals(ba.getBdhIsEditor()))
                .map(ba -> ba.getBdhAuthor().getBdhId())
                .findFirst()
                .orElse(null);
    }
}
