package com.sparta.board_plus.service;

import com.sparta.board_plus.dto.CommentRequestDTO;
import com.sparta.board_plus.dto.CommentResponseDTO;
import com.sparta.board_plus.dto.PostResponseDTO;
import com.sparta.board_plus.entity.Comment;
import com.sparta.board_plus.entity.Post;
import com.sparta.board_plus.entity.User;
import com.sparta.board_plus.entity.UserRoleEnum;
import com.sparta.board_plus.repository.CommentRepository;
import com.sparta.board_plus.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Transactional(readOnly = true)
    public Page<CommentResponseDTO> getComments(User user, int page, int size, String sortBy, boolean isAsc) {
        // 페이징 처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // 사용자 권한 가져와서 ADMIN 이면 전체 조회, USER 면 본인이 추가한 부분 조회
        UserRoleEnum userRoleEnum = user.getRole();

        Page<Comment> commentList;

        if (userRoleEnum == UserRoleEnum.USER) {
            // 사용자 권한이 USER 일 경우
            commentList = commentRepository.findAllByUser(user, pageable);
        } else {
            commentList = commentRepository.findAll(pageable);
        }

        return commentList.map(CommentResponseDTO::new);
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
