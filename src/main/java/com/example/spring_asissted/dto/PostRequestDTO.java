package com.example.spring_asissted.dto;

import com.example.spring_asissted.entity.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class PostRequestDTO {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "Status is required")
    private PostStatus status;

    private Set<Long> tagIds;
}
