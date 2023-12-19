package com.sparta.board_plus.service;

import com.sparta.board_plus.dto.CommentRequestDTO;
import com.sparta.board_plus.dto.CommentResponseDTO;
import com.sparta.board_plus.entity.Comment;
import com.sparta.board_plus.entity.Post;
import com.sparta.board_plus.entity.User;
import com.sparta.board_plus.entity.UserRoleEnum;
import com.sparta.board_plus.repository.CommentRepository;
import com.sparta.board_plus.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;

    public void createComment(UserDetailsImpl userDetails, CommentRequestDTO commentRequestDTO) {
        Post post = postService.findPost(commentRequestDTO.getPostId());

        if(commentRequestDTO.getText().isEmpty()) {
            throw new IllegalArgumentException("댓글을 입력하세요.");
        }

        commentRepository.save(new Comment(userDetails, post, commentRequestDTO));
    }

    @Transactional
    public void updateComment(Long commentId, CommentRequestDTO commentRequestDTO, User user) {
        Comment comment = getComment(user, commentId);

        comment.setText(commentRequestDTO.getText());
    }

    public void deleteComment(User user, Long commentId) {
        Comment comment = getComment(user, commentId);

        commentRepository.delete(comment);
    }

    public Comment getComment(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 ID 입니다."));

        boolean isAdmin = user.getRole().equals(UserRoleEnum.ADMIN);

        if (comment.getUser().getId().equals(user.getId()) || isAdmin) {
            return comment;
        } else {
            throw new RejectedExecutionException("작성자 및 관리자만 댓글을 삭제할 수 있습니다.");
        }
    }
}
