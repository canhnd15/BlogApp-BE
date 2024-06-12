package com.davidnguyen.blogs.utils;

import com.davidnguyen.blogs.entity.Role;
import com.davidnguyen.blogs.enums.ERole;

import java.util.Set;

public class AuthUntil {
    public static Boolean hasAdminOrSuperAdminRole(Set<Role> roles) {
        return roles.stream().anyMatch(role -> ERole.ROLE_ADMIN.equals(role.getName())
                || ERole.ROLE_SUPER_ADMIN.equals(role.getName()));
    }
}
