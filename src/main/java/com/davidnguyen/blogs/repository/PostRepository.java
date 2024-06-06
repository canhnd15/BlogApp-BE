package com.davidnguyen.blogs.repository;

import com.davidnguyen.blogs.entity.Post;
import com.davidnguyen.blogs.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.tags")
    List<Post> findAllWithTags();

    @Query("SELECT p FROM Post p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :title , '%'))")
    List<Post> findByTitleLike(@Param("title") String title);

    @Query("SELECT p FROM Post p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :title , '%'))")
    Page<Post> findByTitle(@Param(value = "title") String title, Pageable pageable);

    Page<Post> findByTags(Set<Tag> tags, Pageable pageable);
}
