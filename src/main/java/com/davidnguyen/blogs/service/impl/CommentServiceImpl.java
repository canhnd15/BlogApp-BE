package com.davidnguyen.blogs.service.impl;

import com.davidnguyen.blogs.dtos.ApiResponseDto;
import com.davidnguyen.blogs.dtos.CommentCreateRequest;
import com.davidnguyen.blogs.dtos.CommentResponseDto;
import com.davidnguyen.blogs.entity.Comment;
import com.davidnguyen.blogs.entity.Post;
import com.davidnguyen.blogs.entity.User;
import com.davidnguyen.blogs.enums.ResponseStatus;
import com.davidnguyen.blogs.exceptions.CommentNotFoundException;
import com.davidnguyen.blogs.exceptions.PostNotFoundException;
import com.davidnguyen.blogs.exceptions.UserNotFoundException;
import com.davidnguyen.blogs.repository.CommentRepository;
import com.davidnguyen.blogs.repository.PostRepository;
import com.davidnguyen.blogs.repository.UserRepository;
import com.davidnguyen.blogs.service.CommentService;
import com.davidnguyen.blogs.singleton.DateFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(UserRepository userRepository, PostRepository postRepository,
                              CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto<?>> create(CommentCreateRequest req) {
        //1. find the user
        User author = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found by id: " + req.getUserId()));

        Post post = postRepository.findById(req.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found by id" + req.getPostId()));

        Comment parentComment = null;
        if(Objects.nonNull(req.getParentId())) {
           parentComment = commentRepository.findById(req.getParentId())
                   .orElseThrow(() -> new CommentNotFoundException("Comment not found by id " + req.getParentId()));
        }

        Comment createdComment = Comment.builder()
                .content(req.getContent())
                .user(author)
                .post(post)
                .createAt(new Date())
                .parent(parentComment)
                .build();

        createdComment = commentRepository.save(createdComment);

        String createdAt = DateFormatter.getInstance().format(createdComment.getCreateAt());
        CommentResponseDto resp = new CommentResponseDto(createdComment.getId(), createdComment.getContent(),
                createdAt, author.getUsername(), post.getId(), req.getParentId());

        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .status(String.valueOf(ResponseStatus.SUCCESS))
                        .response(resp)
                        .message("Comment created successfully!")
                        .build()
        );
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> findCommentByPostId(Long postId) {
        List<Object[]> comments = commentRepository.findCommentByPostId(postId);

        List<CommentResponseDto> response = new ArrayList<>();
        Map<Long, CommentResponseDto> commentMap = new HashMap<>();

        for(Object[] comment: comments) {
            Long id = (Long) comment[0];
            String content = (String) comment[1];
            String createdAt = DateFormatter.getInstance().format(((Timestamp) comment[2]));
            String username = (String) comment[4];
            Long parentId = comment[6] != null ? (Long) comment[6] : null;

            CommentResponseDto dto = new CommentResponseDto(
                    id,
                    content,
                    createdAt,
                    username,
                    postId,
                    parentId,
                    new ArrayList<>()
            );

            commentMap.put(id, dto);

            if(!Objects.nonNull(parentId)) {
                response.add(dto);
            } else {
                CommentResponseDto parentComment = commentMap.get(parentId);
                if(Objects.nonNull(parentComment)) {
                    parentComment.addChildren(dto);
                }
            }
        }

        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .status(String.valueOf(ResponseStatus.SUCCESS))
                        .response(response)
                        .message("Get comment by post " + postId + " successfully!")
                        .build()
        );
    }

    private String convertDate(Date date) {
        return DateFormatter.getInstance().format(date);
    }
}
