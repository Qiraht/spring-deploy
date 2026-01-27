package com.example.spring_asissted.controller;

import com.example.spring_asissted.dto.ApiResponse;
import com.example.spring_asissted.dto.TagRequestDTO;
import com.example.spring_asissted.entity.Tag;
import com.example.spring_asissted.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Tag>>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        return ResponseEntity.ok(ApiResponse.success("Tags retrieved successfully", tags));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Tag>> createTag(@Valid @RequestBody TagRequestDTO request) {
        Tag tag = tagService.createTag(request);
        return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED)
                .body(ApiResponse.success("Tag created successfully", tag));
    }
}
