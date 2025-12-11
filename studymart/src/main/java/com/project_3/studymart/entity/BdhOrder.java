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

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private BdhCustomer customer;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false, length = 255)
    private String shippingAddress;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(length = 255)
    private String note;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<BdhOrderDetail> details;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private BdhPaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "transport_method_id")
    private BdhTransportMethod transportMethod;

    @Column(name = "shipping_fee")
    private Double shippingFee;

    @Column(name = "total_price")
    private Double totalPrice;


}
