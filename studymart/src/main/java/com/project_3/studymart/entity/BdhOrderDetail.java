package com.project_3.studymart.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_details")
@Data
public class BdhOrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private BdhOrder order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private BdhProduct product;

    private Integer quantity;

    private Double unitPrice;

    private Double subtotal;
}
