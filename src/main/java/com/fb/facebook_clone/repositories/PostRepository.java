package com.fb.facebook_clone.repositories;

import com.fb.facebook_clone.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    @Query("SELECT post FROM Post post WHERE post.user.userId = ?1")
    List<Post> getPostsByUserId(UUID userId);
}
