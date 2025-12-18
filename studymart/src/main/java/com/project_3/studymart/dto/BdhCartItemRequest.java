package com.project_3.studymart.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BdhCartItemRequest {
    private Long productId;
    private Integer quantity;
}
