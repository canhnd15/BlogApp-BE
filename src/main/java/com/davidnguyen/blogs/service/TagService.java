package com.davidnguyen.blogs.service;

import com.davidnguyen.blogs.dtos.ApiResponseDto;
import com.davidnguyen.blogs.dtos.TagCreateRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface TagService {
    ResponseEntity<ApiResponseDto<?>> getAllTags();

    ResponseEntity<ApiResponseDto<?>> create(TagCreateRequest req);

    ResponseEntity<ApiResponseDto<?>> delete(Long id,  HttpServletRequest request);

    ResponseEntity<ApiResponseDto<?>> findTagByStatus(String status);
}
