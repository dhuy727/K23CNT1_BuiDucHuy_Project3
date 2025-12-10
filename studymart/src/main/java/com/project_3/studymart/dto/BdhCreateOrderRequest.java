package com.project_3.studymart.dto;

import lombok.Data;

import java.util.List;

@Data
public class BdhCreateOrderRequest {

    private String shippingAddress;
    private String phone;
    private String paymentMethod; // COD, MOMO,...
    private String note;

    private List<BdhCartItemRequest> items;
}
