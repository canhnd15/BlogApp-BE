package com.davidnguyen.blogs.controller;

import com.davidnguyen.blogs.dtos.ApiResponseDto;
import com.davidnguyen.blogs.dtos.PostCreateRequest;
import com.davidnguyen.blogs.dtos.PostUpdateRequest;
import com.davidnguyen.blogs.dtos.PostUpdateStatusRequest;
import com.davidnguyen.blogs.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Post", description = "API for managing blog posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "Create a new post")
    @PostMapping("")
    public ResponseEntity<ApiResponseDto<?>> create(@RequestBody @Valid PostCreateRequest req) {
        return postService.create(req);
    }

    @Operation(summary = "Update post")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<?>> update(@RequestBody @Valid PostUpdateRequest req,
                                                    @PathVariable Long id) {
        return postService.update(req, id);
    }

    @Operation(summary = "Update post's status")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDto<?>> updateStatus(@RequestBody PostUpdateStatusRequest req,
                                                          @PathVariable Long id) {
        return postService.updateStatus(req, id);
    }

    @Operation(summary = "Delete a post by post's id")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<?>> delete(@PathVariable("id") Long id) {
        return postService.delete(id);
    }

    @Operation(summary = "Get post by post's id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<?>> getPostByPostId(@PathVariable Long id) {
        return postService.findPostById(id);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponseDto<?>> getAllPosts() {
        return postService.getAllPost();
    }

    @GetMapping("/paging")
    public ResponseEntity<ApiResponseDto<?>> getAllWithPaging(
            @RequestParam(name = "title") String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return postService.findAllWithPaging(title, page, size);
    }
}
