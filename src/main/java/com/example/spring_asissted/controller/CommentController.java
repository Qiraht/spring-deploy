package com.example.spring_asissted.controller;

import com.example.spring_asissted.dto.ApiResponse;
import com.example.spring_asissted.dto.CommentRequestDTO;
import com.example.spring_asissted.entity.Comment;
import com.example.spring_asissted.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<ApiResponse<Comment>> addComment(@PathVariable Long postId,
            @Valid @RequestBody CommentRequestDTO request) {
        Comment comment = commentService.addComment(postId, request);
        return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED)
                .body(ApiResponse.success("Comment added successfully", comment));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse<List<Comment>>> getCommentsByPost(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(ApiResponse.success("Comments retrieved successfully", comments));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Long>> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok(ApiResponse.success("Comment deleted successfully", id));
    }
}
