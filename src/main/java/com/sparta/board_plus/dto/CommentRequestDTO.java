package com.sparta.board_plus.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {
    private Long postId;
    private String text;
}
