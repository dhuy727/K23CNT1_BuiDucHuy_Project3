package com.project_3.studymart.dto;

import lombok.Data;

@Data
public class BdhCheckoutRequest {
    private String shippingAddress;
    private String phone;
    private String note;

    private Long paymentMethodId;
    private Long transportMethodId;
}
