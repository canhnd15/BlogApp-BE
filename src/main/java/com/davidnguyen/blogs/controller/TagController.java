package com.davidnguyen.blogs.controller;

import com.davidnguyen.blogs.dtos.ApiResponseDto;
import com.davidnguyen.blogs.dtos.TagCreateRequest;
import com.davidnguyen.blogs.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tag")
@Tag(name = "Tags", description = "API for managing blog tags")
public class TagController {
    private final TagService tagService;
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @Operation(summary = "Create a new tag")
    @PostMapping()
    public ResponseEntity<ApiResponseDto<?>> createTag(@RequestBody @Valid TagCreateRequest req) {
        return tagService.create(req);
    }

    @Operation(summary = "Get tags by status.")
    @GetMapping("/{status}")
    public ResponseEntity<ApiResponseDto<?>> getTagByStatus(@PathVariable String status) {
        return tagService.findTagByStatus(status);
    }

    @Operation(summary = "Delete a tag by id", description = "Only role [admin, supper admin] can delete a tag.")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<?>> delete(@PathVariable Long id, HttpServletRequest request) {
        return tagService.delete(id, request);
    }
}
