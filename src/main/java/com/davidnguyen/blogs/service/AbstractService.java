package com.davidnguyen.blogs.service;

import com.davidnguyen.blogs.dtos.ApiResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AbstractService <T> {
    ResponseEntity<ApiResponseDto<?>> create(T sdi);

    ResponseEntity<ApiResponseDto<?>> delete(Long id);

    ResponseEntity<ApiResponseDto<?>> update(T req, Long id);

    T findById(Long id);

    List<T> findAll();

    void deleteById(Long id);
}
