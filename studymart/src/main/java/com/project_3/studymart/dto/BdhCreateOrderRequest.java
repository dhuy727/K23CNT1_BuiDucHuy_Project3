package com.project_3.studymart.dto;

import lombok.Data;

import java.util.List;

@Data
public class BdhCreateOrderRequest {

    private String shippingAddress;
    private String phone;
    private String note;
    private Long transportMethodId;
    private Long paymentMethodId;

    private List<BdhCartItemRequest> items;
}

@Data
class OrderItemRequest {
    private Long productId;
    private int quantity;
}
