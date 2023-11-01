package com.fb.facebook_clone.dto;

import com.fb.facebook_clone.models.Likes;
import com.fb.facebook_clone.utils.Utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record PostRecord(
        UUID postId, String post, UserRecord user, List<CommentRecord> comments, List<Likes> likes,
        LocalDateTime createdAt, LocalDateTime updatedAt
) {
    public int numberOfComments() {
        return comments.size();
    }

    public int numberOfLikes() {
        return likes.stream().filter(
                like -> like.getPost().getPostId().equals(postId)
        ).toList().size();
    }

    public boolean isCommentedByCurrentUser(UserRecord currentUser) {
        return comments.stream().anyMatch(
                comment -> comment.postId().equals(currentUser.userId())
        );
    }

    public boolean isPostedByCurrentUser(UserRecord currentUser) {
        return user.userId().equals(currentUser.userId());
    }

    public boolean isLikedByCurrentUser(UserRecord currentUser) {
        return likes.stream().anyMatch(like ->
                like.getPost().getPostId().equals(postId) && like.getUser().getUserId().equals(currentUser.userId())
        );
    }

    public String postedBy() {
        return user.fullName();
    }

    public String postedByAvatar() {
        return user.avatar();
    }

    public boolean isUpdated() {
        return updatedAt != null;
    }

    public String postTime() {
        if(isUpdated()) {
            return "Posted at: %s, Updated at: %s".formatted(
                    Utils.formatDateTime(createdAt.toString()), Utils.formatDateTime(updatedAt.toString())
            );
        } else {
            return Utils.formatDateTime(createdAt.toString());
        }
    }

    public String likeString(UserRecord currentUser) {
        if(numberOfLikes() == 0) {
            return "Be the first to like this post";
        } else if(isLikedByCurrentUser(currentUser) && numberOfLikes() > 1) {
            return "You and %s like this post".formatted(numberOfLikes() - 1);
        } else if(isLikedByCurrentUser(currentUser)) {
            return "You like this post";
        } else {
            return "%s like this post".formatted(numberOfLikes());
        }
    }

    public String commentString(UserRecord currentUser) {
        if(numberOfComments() == 0) {
            return "Be the first to make a comment";
        } else if(isCommentedByCurrentUser(currentUser) && numberOfComments() > 1) {
            return "You and %s commented this post".formatted(numberOfComments() - 1);
        } else if(isCommentedByCurrentUser(currentUser)) {
            return "You commented this post";
        } else {
            return "%s commented this post".formatted(numberOfComments());
        }
    }
}
