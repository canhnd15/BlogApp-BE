package com.davidnguyen.blogs.service.impl;

import com.davidnguyen.blogs.dtos.*;
import com.davidnguyen.blogs.entity.Post;
import com.davidnguyen.blogs.entity.Tag;
import com.davidnguyen.blogs.entity.User;
import com.davidnguyen.blogs.enums.PostStatus;
import com.davidnguyen.blogs.enums.ResponseStatus;
import com.davidnguyen.blogs.exceptions.PostNotFoundException;
import com.davidnguyen.blogs.exceptions.UserNotFoundException;
import com.davidnguyen.blogs.repository.PostRepository;
import com.davidnguyen.blogs.repository.TagRepository;
import com.davidnguyen.blogs.repository.UserRepository;
import com.davidnguyen.blogs.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto<?>> create(PostCreateRequest req) {
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

        PostResponseDto response = PostResponseDto.builder()
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
                        .status(String.valueOf(ResponseStatus.SUCCESS))
                        .response(response)
                        .build()
        );
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> getAllPost() {
        List<Post> posts = postRepository.findAllWithTags();

        List<PostResponseDto> result = convertToDto(posts);

        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .status(String.valueOf(ResponseStatus.SUCCESS))
                        .response(result)
                        .message("")
                        .build()
        );
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> findAllWithPaging(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Post> posts = postRepository.findByTitle(title, pageable).getContent();

        List<PostResponseDto> result = convertToDto(posts);

        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .status(String.valueOf(ResponseStatus.SUCCESS))
                        .response(result)
                        .message("")
                        .build()
        );
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> findPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id " + id));

        PostResponseDto resp = PostResponseDto.builder()

                .build();
        return null;
    }

    private List<PostResponseDto> convertToDto(List<Post> posts) {
        List<PostResponseDto> result = new ArrayList<>();

        for (Post post: posts) {
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

            PostResponseDto responseDto = PostResponseDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .createAt(post.getCreateAt())
                    .updateAt(post.getUpdatedAt())
                    .status(post.getStatus())
                    .tags(tagResp)
                    .reactions(responseResp)
                    .build();

            result.add(responseDto);
        }

        return result;
    }
}
