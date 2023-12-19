package com.sparta.board_plus.dto;

import com.sparta.board_plus.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class CommentResponseDTO extends CommonResponseDTO {
    private Long commentId;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDTO(Comment comment) {
        this.commentId = comment.getCommentId();
        this.text = comment.getText();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }

}
