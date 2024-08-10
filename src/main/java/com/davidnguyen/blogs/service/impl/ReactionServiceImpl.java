package com.davidnguyen.blogs.service.impl;

import com.davidnguyen.blogs.dtos.ApiResponseDto;
import com.davidnguyen.blogs.entity.Reaction;
import com.davidnguyen.blogs.service.ReactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReactionServiceImpl implements ReactionService {
    @Override
    public ResponseEntity<ApiResponseDto<?>> create(Reaction sdi) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> delete(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> update(Reaction req, Long id) {
        return null;
    }

    @Override
    public Reaction findById(Long id) {
        return null;
    }

    @Override
    public List<Reaction> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(Long id) {

    }
}
