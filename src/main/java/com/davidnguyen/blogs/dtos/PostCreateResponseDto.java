package com.davidnguyen.blogs.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class PostCreateResponseDto {
    private Long id;
    private String title;
    private String status;
    private String content;
    private Date createAt;
    private UserResponseDto user;
    private List<TagResponseDto> tags;
    private List<ReactionResponseDto> reactions;
}
