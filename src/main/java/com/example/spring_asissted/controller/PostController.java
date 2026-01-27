package com.example.spring_asissted.controller;

import com.example.spring_asissted.dto.ApiResponse;
import com.example.spring_asissted.dto.PostRequestDTO;
import com.example.spring_asissted.entity.Post;
import com.example.spring_asissted.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<Post>> createPost(@Valid @RequestBody PostRequestDTO request) {
        Post post = postService.createPost(request);
        return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED)
                .body(ApiResponse.success("Post created successfully", post));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Post>>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(ApiResponse.success("Posts retrieved successfully", posts));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<Post>> getPostBySlug(@PathVariable String slug) {
        Post post = postService.getPostBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success("Post retrieved successfully", post));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Post>> updatePost(@PathVariable Long id,
            @Valid @RequestBody PostRequestDTO request) {
        Post post = postService.updatePost(id, request);
        return ResponseEntity.ok(ApiResponse.success("Post updated successfully", post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Long>> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(ApiResponse.success("Post deleted successfully", id));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Post>>> searchByTag(@RequestParam String tag) {
        List<Post> posts = postService.searchByTag(tag);
        return ResponseEntity.ok(ApiResponse.success("Posts found successfully", posts));
    }
}
