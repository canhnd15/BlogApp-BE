package com.davidnguyen.blogs.repository;

import com.davidnguyen.blogs.enums.ERole;
import com.davidnguyen.blogs.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
}
