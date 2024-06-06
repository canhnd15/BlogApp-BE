package com.davidnguyen.blogs.factory;

import com.davidnguyen.blogs.entity.ERole;
import com.davidnguyen.blogs.entity.Role;
import com.davidnguyen.blogs.exceptions.RoleNotFoundException;
import com.davidnguyen.blogs.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleFactory {
    @Autowired
    RoleRepository roleRepository;

    public Role getInstance(String role) throws RoleNotFoundException {
        switch (role) {
            case "admin" -> {
                return roleRepository.findByName(ERole.ROLE_ADMIN);
            }
            case "user" -> {
                return roleRepository.findByName(ERole.ROLE_USER);
            }
            case "super_admin" -> {
                return roleRepository.findByName(ERole.ROLE_SUPER_ADMIN);
            }
            default -> throw  new RoleNotFoundException("No role found for " +  role);
        }
    }
}
