package com.davidnguyen.blogs.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostUpdateRequest {
    private String title;
    private String content;
    private List<String> tags;
}
