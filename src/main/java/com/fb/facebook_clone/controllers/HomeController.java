package com.fb.facebook_clone.controllers;

import com.fb.facebook_clone.dto.CommentRecord;
import com.fb.facebook_clone.dto.EditRecord;
import com.fb.facebook_clone.dto.PostRecord;
import com.fb.facebook_clone.dto.UserRecord;
import com.fb.facebook_clone.models.Comment;
import com.fb.facebook_clone.models.Likes;
import com.fb.facebook_clone.models.Post;
import com.fb.facebook_clone.models.User;
import com.fb.facebook_clone.repositories.CommentRepository;
import com.fb.facebook_clone.repositories.LikeRepository;
import com.fb.facebook_clone.repositories.PostRepository;
import com.fb.facebook_clone.repositories.UserRepository;
import com.fb.facebook_clone.services.AuthService;
import com.fb.facebook_clone.services.CommentService;
import com.fb.facebook_clone.services.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final PostService postService;
    private final AuthService auth;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final CommentService commentService;
    private final UserRepository repository;

    @GetMapping(name = "Home", value = {"/", "/home"})
    public String showHomePage(HttpSession httpSession, Model model) {
        if(httpSession != null && httpSession.getAttribute("currentUser") != null) {
            UserRecord currentUser = (UserRecord) httpSession.getAttribute("currentUser");
            model.addAttribute("currentUser", currentUser);
            auth.setSession(httpSession, currentUser);

            model.addAttribute("inputText", "What's on your mind, " + currentUser.firstName() + "?");

            List<Post> posts = postRepository.findAll();
            List<PostRecord> postRecords = new ArrayList<>();
            for(Post post : posts) {
                List<Comment> comments = commentRepository.findAll().stream().filter(
                        comment -> comment.getPost().getPostId().equals(post.getPostId())
                ).toList();
                PostRecord postRecord = getPostRecord(post, comments);
                postRecords.add(postRecord);
            }
            postRecords.sort((a, b) -> b.createdAt().compareTo(a.createdAt()));
            postRecords.sort((a, b) -> b.updatedAt().compareTo(a.updatedAt()));
            model.addAttribute("posts", postRecords);

            if(httpSession.getAttribute("view-profile") != null) {
                UserRecord userRecord = (UserRecord) httpSession.getAttribute("view-profile");
                model.addAttribute("viewProfile", userRecord);
            } else {
                httpSession.setAttribute("view-profile", currentUser);
                model.addAttribute("viewProfile", currentUser);
            }

            model.addAttribute("editContent", new EditRecord("", "", ""));
            return "home";
        } else {
            return "redirect:/auth/login";
        }
    }

    private PostRecord getPostRecord(Post post, List<Comment> comments) {
        List<CommentRecord> commentRecords = new ArrayList<>();
        for(Comment comment : comments) {
            CommentRecord record = new CommentRecord(
                    comment.getCommentId(), comment.getComment(), comment.getUser().getUserId(),
                    auth.userRecord(comment.getUser()), comment.getPost().getPostId(), comment.getLikes()
            );
            commentRecords.add(record);
        }

        return new PostRecord(
                post.getPostId(), post.getPost(), auth.userRecord(post.getUser()), commentRecords,
                post.getLikes(), post.getCreatedAt(), post.getUpdatedAt()
        );
    }

    @PostMapping(name = "Show Profile", value = "/show/profile")
    public String showSideProfile(HttpSession httpSession, String userId) {
        Optional<User> user = repository.findById(UUID.fromString(userId));
        user.ifPresent(value -> httpSession.setAttribute("view-profile", value));
        return "redirect:/";
    }

    @PostMapping(name = "Create Post", value = "/post/create")
    public String createPost(HttpSession httpSession, EditRecord edit) {
        if(edit.text() != null) {
            UserRecord currentUser = (UserRecord) httpSession.getAttribute("currentUser");
            repository.findById(currentUser.userId()).ifPresent(value -> {
                postService.createPost(edit.text(), value);
            });
        }
        return "redirect:/";
    }

    @PostMapping(name = "Like Post", value = "/post/like")
    public String likePost(HttpSession httpSession, String postId, String isLiked) {
        if(postId != null && isLiked != null) {
            boolean state = Boolean.getBoolean(isLiked);
            UserRecord currentUser = (UserRecord) httpSession.getAttribute("currentUser");
            if(state) {
                likeRepository.deleteByPostIdAndUserId(currentUser.userId(), UUID.fromString(postId));
            } else {
                repository.findById(currentUser.userId()).ifPresent(user -> {
                    postRepository.findById(UUID.fromString(postId)).ifPresent(post -> {
                        Likes likes = new Likes();
                        likes.setPost(post);
                        likes.setUser(user);
                        likeRepository.save(likes);
                    });
                });
            }
        }
        return "redirect:/";
    }

    @PostMapping(name = "Edit Post", value = "/post/edit")
    public String editPost(Model model, @RequestBody String userId) {
        System.out.println(userId);
        return "redirect:/";
    }

    @PostMapping(name = "Delete Post", value = "/post/delete")
    public String deletePost(String postId) {
        postRepository.deleteById(UUID.fromString(postId));
        return "redirect:/";
    }

    @PostMapping(name = "Create Comment", value = "/comment/create")
    public String createComment(HttpSession httpSession, EditRecord edit) {
        if(edit.text() != null && edit.postId() != null) {
            UserRecord currentUser = (UserRecord) httpSession.getAttribute("currentUser");
            repository.findById(currentUser.userId()).ifPresent(value -> {
                postRepository.findById(UUID.fromString(edit.postId())).ifPresent(post -> {
                    commentService.createComment(edit.text(), value, post);
                });
            });
        }
        return "redirect:/";
    }

    @PostMapping(name = "Like Comment", value = "/comment/like")
    public String likeComment(HttpSession httpSession, String postId, String commentId, String isLiked) {
        if(postId != null && commentId != null && isLiked != null) {
            boolean state = Boolean.getBoolean(isLiked);
            UserRecord currentUser = (UserRecord) httpSession.getAttribute("currentUser");
            if(state) {
                likeRepository.deleteByCommentIdAndUserIdAndPostId(
                        currentUser.userId(), UUID.fromString(postId), Long.getLong(commentId)
                );
            } else {
                repository.findById(currentUser.userId()).ifPresent(user -> {
                    postRepository.findById(UUID.fromString(postId)).ifPresent(post -> {
                        commentRepository.findById(Long.getLong(commentId)).ifPresent(comment -> {
                            Likes likes = new Likes();
                            likes.setPost(post);
                            likes.setUser(user);
                            likes.setComment(comment);
                            likeRepository.save(likes);
                        });
                    });
                });
            }
        }
        return "redirect:/";
    }

    @PostMapping(name = "Edit Comment", value = "/comment/edit")
    public String editComment(Model model, @RequestBody String userId) {
        System.out.println(userId);
        return "redirect:/";
    }

    @PostMapping(name = "Delete Comment", value = "/comment/delete")
    public String deleteComment(String commentId) {
        commentRepository.deleteById(Long.getLong(commentId));
        return "redirect:/";
    }
}
