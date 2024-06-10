package com.davidnguyen.blogs.service;

import com.davidnguyen.blogs.dtos.ApiResponseDto;
import com.davidnguyen.blogs.dtos.CommentCreateRequest;
import com.davidnguyen.blogs.dtos.CommentUpdateRequest;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<ApiResponseDto<?>> create(CommentCreateRequest req);

    ResponseEntity<ApiResponseDto<?>> update(CommentUpdateRequest req, Long id);

    ResponseEntity<ApiResponseDto<?>> delete(Long id);

    ResponseEntity<ApiResponseDto<?>> findCommentByPostId(Long postId);
}
