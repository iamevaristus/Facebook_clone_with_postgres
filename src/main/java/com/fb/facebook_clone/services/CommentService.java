package com.fb.facebook_clone.services;

import com.fb.facebook_clone.models.Comment;
import com.fb.facebook_clone.models.Post;
import com.fb.facebook_clone.models.User;
import com.fb.facebook_clone.repositories.CommentRepository;
import com.fb.facebook_clone.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository repository;

    public void createComment(String content, User user, Post post) {
        Comment comment = new Comment();
        comment.setComment(content);
        comment.setUser(user);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setLikes(new ArrayList<>());
        comment.setPost(post);
        repository.save(comment);
    }
}
