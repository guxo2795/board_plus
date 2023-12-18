package com.sparta.board_plus.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.board_plus.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
public class PostResponseDTO extends CommonResponseDTO{
    private String title;
    private String nickname;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponseDTO(Post post) {
        this.title = post.getTitle();
        this.nickname = post.getUser().getUsername();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
