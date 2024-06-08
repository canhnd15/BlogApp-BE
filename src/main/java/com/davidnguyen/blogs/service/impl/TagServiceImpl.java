package com.davidnguyen.blogs.service.impl;

import com.davidnguyen.blogs.dtos.ApiResponseDto;
import com.davidnguyen.blogs.dtos.TagCreateRequest;
import com.davidnguyen.blogs.dtos.TagCreateResponse;
import com.davidnguyen.blogs.entity.Tag;
import com.davidnguyen.blogs.enums.ResponseStatus;
import com.davidnguyen.blogs.enums.TagStatus;
import com.davidnguyen.blogs.repository.TagRepository;
import com.davidnguyen.blogs.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> getAllTags() {
        List<Tag> tags = tagRepository.findAll();

        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .status(String.valueOf(ResponseStatus.SUCCESS))
                        .response(tags)
                        .message("Get all tags successfully!")
                        .build()
        );
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> create(TagCreateRequest req) {
        //1. check if tag is exist by tag's name.
        Tag existingTag = tagRepository.findByName(req.getName());
        if (existingTag != null) {
            return ResponseEntity.ok(
                    ApiResponseDto.builder()
                            .status(String.valueOf(ResponseStatus.FAIL))
                            .response(null)
                            .message("Tag with name: " + req.getName() + " already exist.")
                            .build()
            );
        }

        //2. create tag
        Tag createdTag = Tag.builder()
                .name(req.getName())
                .status(String.valueOf(TagStatus.PENDING))
                .createAt(new Date())
                .build();

        Tag savedTag = tagRepository.save(createdTag);

        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .status(String.valueOf(ResponseStatus.SUCCESS))
                        .response(TagCreateResponse.builder()
                                .id(savedTag.getId())
                                .name(savedTag.getName())
                                .build())
                        .message("Tag created successfully!")
                        .build()
        );
    }
}
