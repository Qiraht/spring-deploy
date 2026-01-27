package com.example.spring_asissted.service;

import jakarta.persistence.EntityNotFoundException;
import com.example.spring_asissted.dto.PostRequestDTO;
import com.example.spring_asissted.entity.Post;
import com.example.spring_asissted.entity.Tag;
import com.example.spring_asissted.entity.User;
import com.example.spring_asissted.repository.PostRepository;
import com.example.spring_asissted.repository.TagRepository;
import com.example.spring_asissted.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    public Post createPost(PostRequestDTO request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<Tag> tags = new HashSet<>();
        if (request.getTagIds() != null) {
            tags.addAll(tagRepository.findAllById(request.getTagIds()));
        }

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .status(request.getStatus())
                .author(author)
                .tags(tags)
                .build();

        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostBySlug(String slug) {
        return postRepository.findBySlug(slug)
                .orElseThrow(() -> new EntityNotFoundException("Post with slug '" + slug + "' not found"));
    }

    public Post updatePost(Long id, PostRequestDTO request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post with ID " + id + " not found"));

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setStatus(request.getStatus());

        if (request.getTagIds() != null) {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(request.getTagIds()));
            post.setTags(tags);
        }

        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public List<Post> searchByTag(String tagName) {
        return postRepository.findByTagsName(tagName);
    }
}
