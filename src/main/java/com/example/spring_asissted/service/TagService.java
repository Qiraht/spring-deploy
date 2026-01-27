package com.example.spring_asissted.service;

import com.example.spring_asissted.dto.TagRequestDTO;
import com.example.spring_asissted.entity.Tag;
import com.example.spring_asissted.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag createTag(TagRequestDTO request) {
        tagRepository.findByName(request.getName()).ifPresent(tag -> {
            throw new RuntimeException("Tag with name '" + request.getName() + "' already exists");
        });
        Tag tag = Tag.builder()
                .name(request.getName())
                .build();
        return tagRepository.save(tag);
    }
}
