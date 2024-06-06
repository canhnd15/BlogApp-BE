package com.davidnguyen.blogs.service;

import com.davidnguyen.blogs.dtos.ApiResponseDto;
import com.davidnguyen.blogs.dtos.PostCreateRequestDto;
import org.springframework.http.ResponseEntity;

public interface PostService {
    ResponseEntity<ApiResponseDto<?>> create(PostCreateRequestDto req);

    ResponseEntity<ApiResponseDto<?>> getAllPost();

    ResponseEntity<ApiResponseDto<?>> findAllWithPaging(String title, int page, int size);
}
