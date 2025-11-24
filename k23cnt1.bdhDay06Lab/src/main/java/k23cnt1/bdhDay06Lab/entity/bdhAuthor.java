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
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class bdhAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long bdhId;

    String bdhCode;
    String bdhName;
    String bdhDescription;
    String bdhImgUrl;
    String bdhEmail;
    String bdhPhone;
    String bdhAddress;
    boolean bdhIsActive;

    // ===== Quan hệ với bảng trung gian bdh_book_author =====
    @OneToMany(
            mappedBy = "bdhAuthor",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    List<bdhBookAuthor> bdhBookAuthors = new ArrayList<>();

    // (optional) Helper nếu bạn muốn lấy list Book từ Author
    @Transient
    public List<bdhBook> getBooks() {
        List<bdhBook> result = new ArrayList<>();
        if (bdhBookAuthors != null) {
            for (bdhBookAuthor ba : bdhBookAuthors) {
                if (ba.getBdhBook() != null) {
                    result.add(ba.getBdhBook());
                }
            }
        }
        return result;
    }
}
