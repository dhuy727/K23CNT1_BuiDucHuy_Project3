package com.bdh.labguide04.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record KhoaDTO(
        @NotBlank(message = "Mã khoa không được để trống")
        @Size(min = 2, max = 5, message = "Mã khoa từ 2-5 ký tự")
        String makh,

        @NotBlank(message = "Tên khoa không được để trống")
        String tenkh
) {}