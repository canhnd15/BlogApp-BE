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
import com.davidnguyen.blogs.singleton.DateFormatter;
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
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + req.getUserId()));

        List<String> tagName = req.getTags();
        List<Tag> tags = tagRepository.findAllByNameIn(tagName);

        Post post = Post.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .status(String.valueOf(PostStatus.PENDING))
                .isDraft(req.getIsDraft())
                .slug(req.getSlug())
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
                .status(tag.getStatus())
                .build()));

        PostResponseDto response = PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .slug(post.getSlug())
                .status(post.getStatus())
                .createAt(DateFormatter.getInstance().format(post.getCreateAt()))
                .user(UserResponseDto.builder()
                        .id(author.getId())
                        .username(author.getUsername())
                        .email(author.getEmail())
                        .enabled(author.isEnabled())
                        .build())
                .tags(tagsResp)
                .build();

        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .status(String.valueOf(ResponseStatus.SUCCESS))
                        .response(response)
                        .message("Post created successfully!")
                        .build()
        );
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> delete(Long id) {
        postRepository.deleteById(id);

        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .status(String.valueOf(ResponseStatus.SUCCESS))
                        .message("Post deleted successfully!")
                        .build());
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> update(PostUpdateRequest req, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> updateStatus(PostUpdateStatusRequest req, Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id " + id));

        String updatedStatus = req.getStatus();

        if (String.valueOf(PostStatus.DELETE).equals(updatedStatus)) {
            post.setStatus(String.valueOf(PostStatus.DELETE));
        } else if (String.valueOf(PostStatus.PENDING).equals(updatedStatus)) {
            post.setStatus(String.valueOf(PostStatus.PENDING));
        } else if (String.valueOf(PostStatus.CREATED).equals(updatedStatus)) {
            post.setStatus(String.valueOf(PostStatus.CREATED));
        }

        postRepository.save(post);

        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .status(String.valueOf(ResponseStatus.SUCCESS))
                        .message("Update post's status successfully!")
                        .build()
        );
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> getAllPost() {
        List<Post> posts = postRepository.findAllWithTags();

        List<PostResponseDto> result = new ArrayList<>();
        for(Post post: posts) {
            result.add(convertToDto(post));
        }

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

        PostResponseDto resp = convertToDto(post);

        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .status(String.valueOf(ResponseStatus.SUCCESS))
                        .response(resp)
                        .message("Found post with id " + id)
                        .build()
        );
    }

    private PostResponseDto convertToDto(Post post) {
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

        User author = post.getUser();

        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .slug(post.getSlug())
                .content(post.getContent())
                .createAt(Objects.nonNull(post.getCreateAt()) ? DateFormatter.getInstance().format(post.getCreateAt()) : null)
                .updateAt(Objects.nonNull(post.getUpdatedAt()) ? DateFormatter.getInstance().format(post.getUpdatedAt()) : null)
                .user(UserResponseDto.builder()
                        .username(author.getUsername())
                        .id(author.getId())
                        .email(author.getEmail())
                        .enabled(author.isEnabled())
                        .build())
                .status(post.getStatus())
                .tags(tagResp)
                .reactions(responseResp)
                .build();
    }
}
