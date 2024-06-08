package com.davidnguyen.blogs.mapper;

import com.davidnguyen.blogs.dtos.CommentResponseDto;
import com.davidnguyen.blogs.dtos.PostResponseDto;
import com.davidnguyen.blogs.dtos.ReactionResponseDto;
import com.davidnguyen.blogs.dtos.TagResponseDto;
import com.davidnguyen.blogs.entity.Post;

import java.util.List;
import java.util.stream.Collectors;

public class PostToDtoMapper {
    public static PostResponseDto convert(Post post) {

        List<TagResponseDto> tagResp = post.getTags().stream()
                .map(tag -> TagResponseDto.builder()
                        .id(tag.getId())
                        .name(tag.getName())
                        .status(tag.getStatus())
                        .build())
                .collect(Collectors.toList());

        List<ReactionResponseDto> responseResp = post.getReactions().stream()
                .map(reaction -> ReactionResponseDto.builder()
                        .id(reaction.getId())
                        .type(reaction.getType())
                        .build()).collect(Collectors.toList());

        PostResponseDto response = PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createAt(post.getCreateAt())
                .updateAt(post.getUpdatedAt())
                .status(post.getStatus())
                .tags(tagResp)
                .reactions(responseResp)
                .build();

        return response;
    }
}
