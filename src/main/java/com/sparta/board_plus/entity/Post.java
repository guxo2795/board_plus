package com.sparta.board_plus.entity;

import com.sparta.board_plus.dto.PostRequestDTO;
import com.sparta.board_plus.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(nullable = false, length = 5000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    public Post(UserDetailsImpl userDetails, PostRequestDTO postRequestDTO) {
        this.user = userDetails.getUser();
        this.title = postRequestDTO.getTitle();
        this.content = postRequestDTO.getContent();
    }

    public void update(PostRequestDTO postRequestDTO) {
        this.title = postRequestDTO.getTitle();
        this.content = postRequestDTO.getContent();
    }
}
