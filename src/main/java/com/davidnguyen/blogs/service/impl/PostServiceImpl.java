package com.davidnguyen.blogs.service.impl;

import com.davidnguyen.blogs.dtos.*;
import com.davidnguyen.blogs.entity.Post;
import com.davidnguyen.blogs.entity.Tag;
import com.davidnguyen.blogs.entity.User;
import com.davidnguyen.blogs.enums.PostStatus;
import com.davidnguyen.blogs.exceptions.UserNotFoundException;
import com.davidnguyen.blogs.repository.PostRepository;
import com.davidnguyen.blogs.repository.TagRepository;
import com.davidnguyen.blogs.repository.UserRepository;
import com.davidnguyen.blogs.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto<?>> create(PostCreateRequestDto req) {
        User author = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id:" + req.getUserId()));

        List<String> tagName = req.getTags();
        List<Tag> tags = tagRepository.findAllByNameIn(tagName);

        String postStatus = req.getIsDraft() ? String.valueOf(PostStatus.DRAFT) : String.valueOf(PostStatus.PENDING);
        Post post = Post.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .status(postStatus)
                .createAt(new Date())
                .user(author)
                .tags(new HashSet<>(tags))
                .build();
        post = postRepository.save(post);

        // convert tags -> tags response.
        List<TagResponseDto> tagsResp = new ArrayList<>();
        post.getTags().forEach(tag -> tagsResp.add(TagResponseDto.builder()
                        .id(tag.getId())
                        .name(tag.getName())
                .build()));

        // convert reactions -> reactions responses.
        List<ReactionResponseDto> reactionsResp = new ArrayList<>();
        if(Objects.nonNull(post.getReactions())) {
            post.getReactions().forEach(reaction -> reactionsResp.add(ReactionResponseDto.builder()
                            .id(reaction.getId())
                            .type(reaction.getType())
                    .build()));
        }

        PostCreateResponseDto response = PostCreateResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .status(post.getStatus())
                .createAt(post.getCreateAt())
                .user(UserResponseDto.builder()
                        .id(author.getId())
                        .username(author.getUsername())
                        .email(author.getEmail())
                        .enabled(author.isEnabled())
                        .build())
                .tags(tagsResp)
                .reactions(reactionsResp)
                .build();

        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .isSuccess(true)
                        .response(response)
                        .build()
        );
    }
}
