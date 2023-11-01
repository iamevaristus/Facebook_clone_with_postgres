package com.fb.facebook_clone.repositories;

import com.fb.facebook_clone.models.Likes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LikeRepository extends CrudRepository<Likes, Long> {
    @Query("DELETE FROM Likes likes WHERE likes.post = ?2 AND likes.user = ?1")
    void deleteByPostIdAndUserId(UUID userId, UUID postId);

    @Query("DELETE FROM Likes likes WHERE likes.post = ?2 AND likes.user = ?1 AND likes.comment = ?3")
    void deleteByCommentIdAndUserIdAndPostId(UUID userId, UUID postId, Long commentId);
}
