package com.example.spring_asissted.service;

import jakarta.persistence.EntityNotFoundException;
import com.example.spring_asissted.dto.CommentRequestDTO;
import com.example.spring_asissted.entity.Comment;
import com.example.spring_asissted.entity.Post;
import com.example.spring_asissted.entity.User;
import com.example.spring_asissted.repository.CommentRepository;
import com.example.spring_asissted.repository.PostRepository;
import com.example.spring_asissted.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Comment addComment(Long postId, CommentRequestDTO request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post with ID " + postId + " not found"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .post(post)
                .user(user)
                .build();

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post with ID " + postId + " not found"));
        return commentRepository.findAllByPost(post);
    }

    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new EntityNotFoundException("Comment with ID " + id + " not found");
        }
        commentRepository.deleteById(id);
    }
}
