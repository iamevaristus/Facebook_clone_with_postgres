package com.fb.facebook_clone.services;

import com.fb.facebook_clone.models.Post;
import com.fb.facebook_clone.models.User;
import com.fb.facebook_clone.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository repository;

    public void createPost(String content, User user) {
        Post post = new Post();
        post.setPost(content);
        post.setComments(new ArrayList<>());
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);
        post.setLikes(new ArrayList<>());
        repository.save(post);
    }
}
