package com.davidnguyen.blogs.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReactionResponseDto {
    private Long id;
    private String type;
}
