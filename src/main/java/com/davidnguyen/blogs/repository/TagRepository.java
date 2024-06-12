package com.davidnguyen.blogs.repository;

import com.davidnguyen.blogs.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findTagsByStatus(String status);

    Tag findByName(String name);

    List<Tag> findAllByNameIn(List<String> tagNames);
}
