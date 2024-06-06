package com.davidnguyen.blogs.service;

import com.davidnguyen.blogs.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    boolean existByUsername(String username);
    boolean existByEmail(String email);
    void save(User user);
}
