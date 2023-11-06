package com.fb.facebook_clone.services;

import com.fb.facebook_clone.dto.CommentRecord;
import com.fb.facebook_clone.dto.PostRecord;
import com.fb.facebook_clone.dto.UserRecord;
import com.fb.facebook_clone.enums.Gender;
import com.fb.facebook_clone.exceptions.AuthException;
import com.fb.facebook_clone.models.Comment;
import com.fb.facebook_clone.models.Post;
import com.fb.facebook_clone.models.User;
import com.fb.facebook_clone.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {
    private final UserRepository repository;

    @Autowired
    public AuthService(UserRepository repository) {
        this.repository = repository;
    }

    public User loginWithEmail(String password, String email) throws AuthException {
        User user = repository.loginWithEmailAndPassword(email, password);
        if(user != null) {
            return user;
        }
        throw new AuthException("Incorrect email address or password!");
    }

    public User loginWithUsername(String password, String username) throws AuthException {
        User user = repository.loginWithUsernameAndPassword(username, password);
        if(user != null) {
            return user;
        }
        throw new AuthException("Incorrect username or password!");
    }

    public User signup(
            String emailAddress, String password, Gender gender,
            String firstName, String lastName, String username
    ) throws AuthException {
        if(repository.checkIfEmailExists(emailAddress) == null) {
            if(repository.checkIfUsernameExists(username) == null) {
                User user = new User();
                user.setEmailAddress(emailAddress);
                user.setUsername(username);
                user.setGender(gender);
                user.setPassword(password);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setCreatedAt(LocalDateTime.now());
                repository.save(user);
                return user;
            } else {
                throw new AuthException("Username already exists");
            }
        } else {
            throw new AuthException("Email address already exists");
        }
    }

    public int logout(UUID userId) {
        if(repository.existsById(userId)) {
            return 200;
        }
        return 100;
    }

    public UserRecord userRecord(User user) {
        return new UserRecord(
                user.getFirstName(), user.getLastName(), user.getUsername(),
                user.getEmailAddress(), user.getUserId(), user.getGender(),
                user.getPosts(), user.getCreatedAt()
        );
    }

    public List<CommentRecord> commentRecords(List<Comment> comments) {
        List<CommentRecord> records = new ArrayList<>();
        comments.forEach(comment -> {
            CommentRecord record = new CommentRecord(
                    comment.getCommentId(), comment.getComment(), comment.getUser().getUserId(),
                    userRecord(comment.getUser()), comment.getPost().getPostId(), comment.getLikes()
            );
            records.add(record);
        });
        return records;
    }

    public void setSession(HttpSession httpSession, UserRecord user) {
        httpSession.setAttribute("currentUser", user);
    }
}
