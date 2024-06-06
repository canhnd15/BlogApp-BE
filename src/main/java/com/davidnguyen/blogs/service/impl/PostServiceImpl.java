package com.davidnguyen.blogs.service.impl;

import com.davidnguyen.blogs.dtos.PostCreateRequestDto;
import com.davidnguyen.blogs.dtos.PostCreateResponseDto;
import com.davidnguyen.blogs.entity.Post;
import com.davidnguyen.blogs.entity.User;
import com.davidnguyen.blogs.exceptions.UserNotFoundException;
import com.davidnguyen.blogs.repository.PostRepository;
import com.davidnguyen.blogs.repository.UserRepository;
import com.davidnguyen.blogs.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    @Transactional
    public PostCreateResponseDto createPost(PostCreateRequestDto req) {
        User author = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id:" + req.getUserId()));

//        Set<Tag> tags = req.getTags();

        Post post = Post.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .createAt(new Date())
                .user(author)
                .tags(null)
                .build();
        return null;
    }
}
