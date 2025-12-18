package com.project_3.studymart.entity;

import com.project_3.studymart.enums.OrderStatus;
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
    @JoinColumn(name = "users_id", nullable = false)
    private BdhUser user;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false)
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status;

    @Column(nullable = false, length = 255)
    private String shippingAddress;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(length = 255)
    private String note;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BdhOrderDetail> details;

    public void setDetails(List<BdhOrderDetail> details) {
        this.details = details;
        if (details != null) {
            details.forEach(d -> d.setOrder(this));
        }
    }

    public void addDetail(BdhOrderDetail d) {
        this.details.add(d);
        d.setOrder(this);
    }

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
