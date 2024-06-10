package com.davidnguyen.blogs.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostCreateRequest {
    @NotBlank(message = "Title is not allow blank.")
    @NotNull(message = "Title is required.")
    private String title;

    private Boolean isDraft;

    @NotNull(message = "Post's title is required.")
    private String content;

    @NotNull(message = "Slug is required.")
    @NotBlank(message = "Slug is not allow blank.")
    private String slug;

    @NotNull(message = "User's id is required.")
    private Long userId;

    @NotNull(message = "Post is required at least one tag.")
    private List<String> tags;
}
