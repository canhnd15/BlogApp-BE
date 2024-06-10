package com.davidnguyen.blogs.service;

import com.davidnguyen.blogs.dtos.ApiResponseDto;
import com.davidnguyen.blogs.dtos.PostCreateRequest;
import org.springframework.http.ResponseEntity;

public interface PostService {
    ResponseEntity<ApiResponseDto<?>> create(PostCreateRequest req);

    ResponseEntity<ApiResponseDto<?>> delete(Long id);

    ResponseEntity<ApiResponseDto<?>> getAllPost();

    ResponseEntity<ApiResponseDto<?>> findAllWithPaging(String title, int page, int size);

    ResponseEntity<ApiResponseDto<?>> findPostById(Long id);
}
