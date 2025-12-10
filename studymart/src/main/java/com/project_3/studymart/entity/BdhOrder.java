package com.project_3.studymart.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class BdhOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Người đặt hàng
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private BdhCustomer customer;

    // Thời gian đặt
    @Column(nullable = false)
    private LocalDateTime orderDate;

    // Tổng tiền
    @Column(nullable = false)
    private Double totalAmount;

    // TRẠNG THÁI: PENDING, PROCESSING, SHIPPING, COMPLETED, CANCELED
    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false, length = 255)
    private String shippingAddress;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(length = 100)
    private String paymentMethod; // COD, MOMO, VNPAY...

    @Column(length = 255)
    private String note;

    // Danh sách sản phẩm trong đơn
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<BdhOrderDetail> details;
}
