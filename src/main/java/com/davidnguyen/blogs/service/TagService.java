package com.davidnguyen.blogs.service;

import com.davidnguyen.blogs.dtos.ApiResponseDto;
import com.davidnguyen.blogs.dtos.TagCreateRequest;
import org.springframework.http.ResponseEntity;

public interface TagService {
    ResponseEntity<ApiResponseDto<?>> getAllTags();
    ResponseEntity<ApiResponseDto<?>> createTag(TagCreateRequest req);
}
