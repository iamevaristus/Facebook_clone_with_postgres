package com.fb.facebook_clone.models;

import com.fb.facebook_clone.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
public class User extends Moment{
    @Id
    @Column(unique = true, name = "user_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID userId;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String emailAddress;
    private Gender gender;
    private String password;
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.EAGER)
    List<Post> posts = new ArrayList<>();
    @OneToMany(orphanRemoval = true, mappedBy = "user", fetch = FetchType.EAGER)
    private List<Likes> likes;
}
