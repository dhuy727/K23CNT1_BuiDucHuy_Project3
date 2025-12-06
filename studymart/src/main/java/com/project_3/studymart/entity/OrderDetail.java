package com.project_3.studymart.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_details")
@Data
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Thuộc về Order nào
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Sản phẩm nào
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity;

    private Double unitPrice;

    private Double subtotal;
}
