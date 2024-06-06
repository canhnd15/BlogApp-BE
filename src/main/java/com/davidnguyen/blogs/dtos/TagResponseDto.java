package com.davidnguyen.blogs.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagResponseDto {
    private Long id;
    private String name;
}
