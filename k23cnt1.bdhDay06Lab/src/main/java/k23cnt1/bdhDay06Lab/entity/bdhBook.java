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
public class bdhBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bdhId;
    private String bdhCode;
    private String bdhName;
    private String bdhDescription;
    private String bdhImgUrl;
    private Integer bdhQuantity;
    private Double bdhPrice;
    private Boolean bdhIsActive;
    //thiết kế mối quan hệ với bảng bdhAuthor
    @ManyToMany
    @JoinTable(
            name = "bdh_book_author",
            joinColumns = @JoinColumn(name = "bdhBookId"),
            inverseJoinColumns = @JoinColumn(name = "bdhAuthorId")
    )
    private List<bdhAuthor> bdhAuthor = new ArrayList<>();
}
