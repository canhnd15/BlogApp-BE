package com.davidnguyen.blogs.controller;

import com.davidnguyen.blogs.dtos.ApiResponseDto;
import com.davidnguyen.blogs.dtos.CommentCreateRequest;
import com.davidnguyen.blogs.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponseDto<?>> createComment(@RequestBody @Valid CommentCreateRequest request) {
        return commentService.create(request);
    }

    @GetMapping("/post/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponseDto<?>> getCommentByPostId(@PathVariable("id") Long id) {
        return commentService.findCommentByPostId(id);
    }
}
