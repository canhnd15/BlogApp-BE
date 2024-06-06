package com.davidnguyen.blogs.controller;

import com.davidnguyen.blogs.dtos.ApiResponseDto;
import com.davidnguyen.blogs.dtos.TagCreateRequest;
import com.davidnguyen.blogs.service.TagService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponseDto<?>> createTag(@RequestBody @Valid TagCreateRequest req) {
        return tagService.create(req);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponseDto<?>> findAll() {
        return tagService.getAllTags();
    }
}
