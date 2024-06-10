package com.davidnguyen.blogs.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CommentUpdateRequest {
    @NotNull(message = "comment's content is required")
    @NotBlank(message = "comment is not allowed blank")
    private String content;
}
