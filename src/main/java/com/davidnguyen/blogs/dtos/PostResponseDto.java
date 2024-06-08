package com.davidnguyen.blogs.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class PostResponseDto {
    private Long id;
    private String title;
    private String status;
    private String content;
    private Date createAt;
    private Date updateAt;
    private UserResponseDto user;
    private List<TagResponseDto> tags;
    private List<CommentResponseDto> comments;
    private List<ReactionResponseDto> reactions;
}
