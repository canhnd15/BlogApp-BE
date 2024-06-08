package com.davidnguyen.blogs.controller;

import com.davidnguyen.blogs.dtos.ApiResponseDto;
import com.davidnguyen.blogs.dtos.PostCreateRequest;
import com.davidnguyen.blogs.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponseDto<?>> create(@RequestBody @Valid PostCreateRequest req) {
        return postService.create(req);
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

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponseDto<?>> getPostById(@PathVariable Long id) {
        return postService.findPostById(id);
    }
}
