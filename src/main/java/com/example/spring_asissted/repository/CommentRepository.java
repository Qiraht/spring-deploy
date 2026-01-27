package com.example.spring_asissted.repository;

import com.example.spring_asissted.entity.Comment;
import com.example.spring_asissted.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
}
