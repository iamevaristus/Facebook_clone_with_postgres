package com.fb.facebook_clone.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "comments")
@Data
public class Comment extends Moment{
    @Id
    @SequenceGenerator(name = "comment_seq", sequenceName = "comment_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "comment_seq")
    @Column(name = "comment_id")
    private Long commentId;
    @Column(columnDefinition = "TEXT")
    private String comment;
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "user_id",
            foreignKey = @ForeignKey(name = "comment_user_id")
    )
    private User user;
    @ManyToOne
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "post_id",
            foreignKey = @ForeignKey(name = "comment_post_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;
    @OneToMany(mappedBy = "comment", orphanRemoval = true)
    private List<Likes> likes;
}