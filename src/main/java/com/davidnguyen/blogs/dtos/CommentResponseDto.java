package com.davidnguyen.blogs.dtos;

import lombok.*;

import java.util.List;

@Setter
@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private String createAt;
    private String username;
    private Long postId;
    private Long parentId;
    private List<CommentResponseDto> children;

    public CommentResponseDto(Long id, String content, String createAt, String username, Long postId, Long parentId) {
        this.id = id;
        this.content = content;
        this.createAt = createAt;
        this.username = username;
        this.postId = postId;
        this.parentId = parentId;
    }

    public CommentResponseDto(Long id, String content, String createAt, String username, Long postId, Long parentId, List<CommentResponseDto> children) {
        this.id = id;
        this.content = content;
        this.createAt = createAt;
        this.username = username;
        this.postId = postId;
        this.parentId = parentId;
        this.children = children;
    }

    public void addChildren(CommentResponseDto child) {
        this.children.add(child);
    }
}
