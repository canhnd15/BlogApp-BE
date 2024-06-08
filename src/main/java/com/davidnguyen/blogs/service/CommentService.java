package com.davidnguyen.blogs.service;

import com.davidnguyen.blogs.dtos.ApiResponseDto;
import com.davidnguyen.blogs.dtos.CommentCreateRequest;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<ApiResponseDto<?>> create(CommentCreateRequest req);

    ResponseEntity<ApiResponseDto<?>> findCommentByPostId(Long postId);
}
