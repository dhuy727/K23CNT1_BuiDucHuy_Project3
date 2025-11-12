package com.bdh.labguide04.dto;

import jakarta.validation.constraints.*;

public record MonHocDTO(
        @NotBlank(message = "Mã môn không được để trống")
        @Size(min = 2, max = 10, message = "Mã môn từ 2-10 ký tự")
        String mamh,

        @NotBlank(message = "Tên môn không được để trống")
        String tenmh,

        @Min(value = 45, message = "Số tiết tối thiểu 45")
        @Max(value = 75, message = "Số tiết tối đa 75")
        int sotiet
) {}