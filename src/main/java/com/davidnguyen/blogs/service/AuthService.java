package com.davidnguyen.blogs.service;

import com.davidnguyen.blogs.dtos.ApiResponseDto;
import com.davidnguyen.blogs.dtos.SignInRequestDto;
import com.davidnguyen.blogs.dtos.SignUpRequestDto;
import com.davidnguyen.blogs.exceptions.RoleNotFoundException;
import com.davidnguyen.blogs.exceptions.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ResponseEntity<ApiResponseDto<?>> signUp(SignUpRequestDto signUpRequestDto) throws UserAlreadyExistsException, RoleNotFoundException;
    ResponseEntity<ApiResponseDto<?>> signIn(SignInRequestDto signInRequestDto);
}
