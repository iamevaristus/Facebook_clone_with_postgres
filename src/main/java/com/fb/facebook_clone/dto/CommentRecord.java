package com.fb.facebook_clone.dto;

import com.fb.facebook_clone.models.Likes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record CommentRecord(Long commentId, String comment, UUID userId, UserRecord user, UUID postId, List<Likes> likes) {
    public boolean isCommentedByCurrentUser(UserRecord currentUser) {
        return user.userId().equals(currentUser.userId());
    }

    public boolean isLikedByCurrentUser(UserRecord currentUser) {
        return likes.stream().anyMatch(
                like -> like.getComment().getCommentId().equals(commentId)
                        && like.getUser().getUserId().equals(currentUser.userId())
        );
    }

    public int numberOfLikes() {
        return likes.stream().filter(
                like -> like.getComment().getCommentId().equals(commentId)).toList().size();
    }

    public String commentedBy() {
        return user.fullName();
    }

    public String commentedByAvatar() {
        return user.avatar();
    }

    public Long currentUserCommentLikeId(UserRecord currentUser) {
        for(Likes like : likes) {
            if(like.getUser().getUserId().equals(currentUser.userId())
                    && like.getPost().getPostId().equals(postId)
                    && like.getComment().getCommentId().equals(commentId)
            ) {
                return like.getId();
            }
        }
        return 0L;
    }

    public String editComment() {
        return comment + "***COMMENTED***" + commentId + "PostId" + postId;
    }

    public String likeString() {
        return numberOfLikes() + " likes";
    }
}
