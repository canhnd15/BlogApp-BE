package com.davidnguyen.blogs.service.impl;

import com.davidnguyen.blogs.dtos.ApiResponseDto;
import com.davidnguyen.blogs.dtos.TagCreateRequest;
import com.davidnguyen.blogs.dtos.TagCreateResponse;
import com.davidnguyen.blogs.dtos.TagResponseDto;
import com.davidnguyen.blogs.entity.Role;
import com.davidnguyen.blogs.entity.Tag;
import com.davidnguyen.blogs.entity.User;
import com.davidnguyen.blogs.enums.ERole;
import com.davidnguyen.blogs.enums.ResponseStatus;
import com.davidnguyen.blogs.enums.TagStatus;
import com.davidnguyen.blogs.exceptions.UserNotFoundException;
import com.davidnguyen.blogs.repository.TagRepository;
import com.davidnguyen.blogs.repository.UserRepository;
import com.davidnguyen.blogs.security.jwt.JwtUtils;
import com.davidnguyen.blogs.service.TagService;
import com.davidnguyen.blogs.utils.AuthUntil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    public TagServiceImpl(TagRepository tagRepository, JwtUtils jwtUtils, UserRepository userRepository) {
        this.tagRepository = tagRepository;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> getAllTags() {
        List<Tag> tags = tagRepository.findTagsByStatus(String.valueOf(TagStatus.CREATED));

        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .status(String.valueOf(ResponseStatus.SUCCESS))
                        .response(tags)
                        .message("Get all tags successfully!")
                        .build()
        );
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> create(TagCreateRequest req) {
        //1. check if tag is exist by tag's name.
        Tag existingTag = tagRepository.findByName(req.getName());
        if (existingTag != null) {
            return ResponseEntity.ok(
                    ApiResponseDto.builder()
                            .status(String.valueOf(ResponseStatus.FAIL))
                            .response(null)
                            .message("Tag with name: " + req.getName() + " already exist.")
                            .build()
            );
        }

        //2. create tag
        Tag createdTag = Tag.builder()
                .name(req.getName())
                .status(String.valueOf(TagStatus.PENDING))
                .createAt(new Date())
                .build();

        Tag savedTag = tagRepository.save(createdTag);

        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .status(String.valueOf(ResponseStatus.SUCCESS))
                        .response(TagCreateResponse.builder()
                                .id(savedTag.getId())
                                .name(savedTag.getName())
                                .build())
                        .message("Tag created successfully!")
                        .build()
        );
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto<?>> delete(Long id,  HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        String email = jwtUtils.getUserNameFromJwtToken(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        Set<Role> roles = user.getRoles();
        Boolean isAdmin = AuthUntil.hasAdminOrSuperAdminRole(roles);

        if(isAdmin) tagRepository.deleteById(id);

        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .status(String.valueOf(ResponseStatus.SUCCESS))
                        .message("Delete tag successfully!")
                        .build()
        );
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> findTagByStatus(String status) {
        List<Tag> tags = tagRepository.findTagsByStatus(status);

        List<TagResponseDto> resp = tags.stream().map(tag -> TagResponseDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .status(tag.getStatus())
                .build()).collect(Collectors.toList());

        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .status(String.valueOf(ResponseStatus.SUCCESS))
                        .response(resp)
                        .message(String.format("Get tags by status %s successfully!", status))
                        .build()
        );
    }
}
