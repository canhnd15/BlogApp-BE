package com.davidnguyen.blogs.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class PostCreateRequestDto {
    private String title;
    private String content;
    private Long userId;
    private Set<String> tags;
}
