package com.sparta.board_plus.entity;

import com.sparta.board_plus.dto.CommentRequestDTO;
import com.sparta.board_plus.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column
    private String text;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(UserDetailsImpl userDetails, Post post, CommentRequestDTO commentRequestDTO) {
        this.user = userDetails.getUser();
        this.post = post;
        this.text = commentRequestDTO.getText();
    }

    public void setText(String text) {
        this.text = text;
    }





}
