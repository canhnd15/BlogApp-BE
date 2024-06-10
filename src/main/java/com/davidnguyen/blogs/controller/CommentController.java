package com.davidnguyen.blogs.controller;

import com.davidnguyen.blogs.dtos.ApiResponseDto;
import com.davidnguyen.blogs.dtos.CommentCreateRequest;
import com.davidnguyen.blogs.dtos.CommentUpdateRequest;
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
    public ResponseEntity<ApiResponseDto<?>> create(@RequestBody @Valid CommentCreateRequest request) {
        return commentService.create(request);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDto<?>> update(@RequestBody @Valid CommentUpdateRequest request,
                                                    @PathVariable("id") Long id) {
        return commentService.update(request, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<?>> delete(@PathVariable Long id) {
        return commentService.delete(id);
    }

    @GetMapping("/post/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponseDto<?>> getCommentByPostId(@PathVariable("id") Long id) {
        return commentService.findCommentByPostId(id);
    }
}
