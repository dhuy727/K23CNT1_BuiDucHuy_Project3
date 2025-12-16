package com.project_3.studymart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BdhSessionCartItem {
    private Long productId;
    private Integer quantity;
}
