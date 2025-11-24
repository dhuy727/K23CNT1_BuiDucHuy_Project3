package k23cnt1.bdhDay06Lab.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "bdh_book_author")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class bdhBookAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "bdh_book_id")
    bdhBook bdhBook;

    @ManyToOne
    @JoinColumn(name = "bdh_author_id")
    bdhAuthor bdhAuthor;

    @Column(name = "bdh_is_editor")
    Boolean bdhIsEditor;   // true = chủ biên, false = đồng tác giả
}
