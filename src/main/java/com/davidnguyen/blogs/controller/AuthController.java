package com.davidnguyen.blogs.controller;

import com.davidnguyen.blogs.dtos.ApiResponseDto;
import com.davidnguyen.blogs.dtos.SignInRequestDto;
import com.davidnguyen.blogs.dtos.SignUpRequestDto;
import com.davidnguyen.blogs.exceptions.RoleNotFoundException;
import com.davidnguyen.blogs.exceptions.UserAlreadyExistsException;
import com.davidnguyen.blogs.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "API for sign-in and sign-up")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponseDto<?>> registerUser(@RequestBody @Valid SignUpRequestDto signUpRequestDto)
            throws UserAlreadyExistsException, RoleNotFoundException {
        return authService.signUp(signUpRequestDto);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponseDto<?>> signInUser(@RequestBody @Valid SignInRequestDto signInRequestDto){
        return authService.signIn(signInRequestDto);
    }
}
