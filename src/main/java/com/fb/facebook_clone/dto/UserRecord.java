package com.fb.facebook_clone.dto;

import com.fb.facebook_clone.enums.Gender;
import com.fb.facebook_clone.models.Post;
import com.fb.facebook_clone.utils.Utils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UserRecord(
        String firstName, String lastName, String username, String emailAddress,
        UUID userId, Gender gender, List<Post> posts, LocalDateTime joinedAt
) {
    public String fullName() {
        return firstName + " " + lastName;
    }

    public String nameAbbrev() {
        return String.valueOf(firstName.charAt(0)) + String.valueOf(lastName.charAt(0));
    }

    public String avatar() {
        if(gender == Gender.MALE) {
            // 🧑🏽‍ 👩🏽‍🦱 🧑🏽‍🦱 👩🏽‍
            return "\uD83E\uDDD1\uD83C\uDFFD\u200D\uD83E\uDDB1";
        } else if(gender == Gender.FEMALE) {
            return "\uD83D\uDC69\uD83C\uDFFD\u200D\uD83E\uDDB1";
        } else {
            return "\uD83E\uDDD1\uD83C\uDFFD\u200D";
        }
    }

    public String postsIntro() {
        return "\uD83D\uDD8A Posts from %s".formatted(firstName);
    }

    public String createPost() {
        return "%s create your fun post  \uD83D\uDD8A".formatted(firstName);
    }

    public String editPost() {
        return "Hey, %s edit your fun post  \uD83D\uDD8A".formatted(firstName);
    }

    public String editComment() {
        return "Hey, %s you can now edit your comment  \uD83D\uDD8A".formatted(firstName);
    }

    public String noPosts() {
        return firstName + " start sharing your fun";
    }

    public String joinedMoment() {
        return "Joined: %s".formatted(Utils.formatDateTime(joinedAt.toString()));
    }

    public String totalPostsMade() {
        return "Total posts made: %s".formatted(posts.size());
    }
}
