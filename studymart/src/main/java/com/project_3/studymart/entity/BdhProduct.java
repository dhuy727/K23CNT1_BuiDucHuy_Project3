package com.project_3.studymart.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class BdhProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Double price;          // giá bán

    @Column(nullable = false)
    private Integer quantity;      // số lượng tồn kho

    @Column(length = 100)
    private String brand;          // thương hiệu (Thiên Long, Deli,...)

    @Column(name = "image_url", length = 255)
    private String imageUrl;       // link ảnh

    @Column(nullable = false)
    private Boolean active = true; // có được bán hay không

    @ManyToOne
    @JoinColumn(name = "category_id")
    private BdhCategory category;     // mỗi sản phẩm thuộc 1 danh mục
}
