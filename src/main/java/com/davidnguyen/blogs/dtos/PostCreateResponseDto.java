package com.davidnguyen.blogs.dtos;

import com.davidnguyen.blogs.entity.Reaction;
import com.davidnguyen.blogs.entity.Tag;
import com.davidnguyen.blogs.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Builder
public class PostCreateResponseDto {
    private Long id;
    private String title;
    private String content;
    private Date createAt;
    private User user;
    private Set<Tag> tags;
    private Set<Reaction> reactions;
}
