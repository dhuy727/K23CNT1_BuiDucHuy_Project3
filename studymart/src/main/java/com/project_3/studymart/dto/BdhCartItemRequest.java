package com.project_3.studymart.dto;

import lombok.Data;

@Data
public class BdhCartItemRequest {
    private Long productId;
    private Integer quantity;
}
