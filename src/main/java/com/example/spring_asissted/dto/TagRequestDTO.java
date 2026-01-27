package com.example.spring_asissted.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TagRequestDTO {
    @NotBlank(message = "Tag name is required")
    private String name;
}
