package com.davidnguyen.blogs.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommentCreateRequest {
    @NotNull(message = "comment's content is required")
    @NotBlank(message = "comment is not allowed blank")
    private String content;

    @NotNull(message = "userId is required")
    private Long userId;

    @NotNull(message = "postId is required")
    private Long postId;

    private Long parentId;
}
