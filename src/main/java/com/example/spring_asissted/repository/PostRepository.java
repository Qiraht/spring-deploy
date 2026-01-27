package com.example.spring_asissted.repository;

import com.example.spring_asissted.entity.Post;
import com.example.spring_asissted.entity.PostStatus;
import com.example.spring_asissted.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Scopes / Common Queries
    Optional<Post> findBySlug(String slug);

    List<Post> findAllByStatus(PostStatus status);

    List<Post> findAllByAuthor(User author);

    List<Post> findTop10ByOrderByCreatedAtDesc();

    List<Post> findByTagsName(String tagName);
}
