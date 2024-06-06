package com.davidnguyen.blogs.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostCreateRequestDto {
    @NotBlank(message = "Post's title is not allow blank.")
    @NotNull(message = "Post's title is required.")
    private String title;

    private Boolean isDraft;

    @NotNull(message = "Post's title is required.")
    private String content;

    @NotNull(message = "User's id is required.")
    private Long userId;

    @NotNull(message = "Post is required at least one tag.")
    private List<String> tags;
}
