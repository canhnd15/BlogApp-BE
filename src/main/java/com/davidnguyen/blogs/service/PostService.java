package com.davidnguyen.blogs.service;

import com.davidnguyen.blogs.dtos.PostCreateRequestDto;
import com.davidnguyen.blogs.dtos.PostCreateResponseDto;

public interface PostService {
    PostCreateResponseDto createPost(PostCreateRequestDto req);
}
