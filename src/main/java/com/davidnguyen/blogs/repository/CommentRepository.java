package com.davidnguyen.blogs.repository;

import com.davidnguyen.blogs.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(
            value = """
                    WITH RECURSIVE comment_tree AS (
                        SELECT c.id, c.content, c.created_at, c.user_id, u.username, c.post_id, c.parent_id
                        FROM comment c join user u on c.user_id = u.id
                        WHERE c.post_id = :postId AND c.parent_id IS NULL
                        UNION ALL
                        SELECT c.id, c.content, c.created_at, c.user_id, u.username, c.post_id, c.parent_id
                        FROM comment c join user u on c.user_id = u.id
                                 INNER JOIN comment_tree ct ON c.parent_id = ct.id
                    )
                    SELECT * FROM comment_tree""",
            nativeQuery = true
    )
    List<Object[]> findCommentByPostId(@Param("postId") Long postId);
}
