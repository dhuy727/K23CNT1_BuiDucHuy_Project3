package com.project_3.studymart.dto;

import lombok.Data;

@Data
public class BdhProductForm {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String brand;
    private String imageUrl;
    private Boolean active;
    private Long categoryId;
}
