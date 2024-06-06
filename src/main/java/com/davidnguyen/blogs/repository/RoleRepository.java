package com.davidnguyen.blogs.repository;

import com.davidnguyen.blogs.entity.ERole;
import com.davidnguyen.blogs.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
}
