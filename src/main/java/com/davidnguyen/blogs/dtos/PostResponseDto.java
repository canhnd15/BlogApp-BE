package com.davidnguyen.blogs.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostResponseDto {
    private Long id;
    private String title;
    private String status;
    private String content;
    private String slug;
    private String createAt;
    private String updateAt;
    private UserResponseDto user;
    private List<TagResponseDto> tags;
    private List<ReactionResponseDto> reactions;
}
