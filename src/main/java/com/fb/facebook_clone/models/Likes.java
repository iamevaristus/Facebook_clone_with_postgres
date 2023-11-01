package com.fb.facebook_clone.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "likes")
@Getter
@Setter
public class Likes {
    @Id
    @SequenceGenerator(sequenceName = "id_sequence", name = "id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence")
    private Long id;
    @ManyToOne
    @JoinColumn(
            name = "comment_id",
            referencedColumnName = "comment_id",
            foreignKey = @ForeignKey(name = "like_comment_id")
    )
    private Comment comment;
    @ManyToOne
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "post_id",
            foreignKey = @ForeignKey(name = "like_post_id")
    )
    private Post post;
    @OneToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "user_id",
            foreignKey = @ForeignKey(name = "like_user_id")
    )
    private User user;

    public Likes() {}
}
