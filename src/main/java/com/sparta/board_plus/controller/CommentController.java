package com.sparta.board_plus.controller;

import com.sparta.board_plus.dto.CommentRequestDTO;
import com.sparta.board_plus.dto.CommentResponseDTO;
import com.sparta.board_plus.dto.CommonResponseDTO;
import com.sparta.board_plus.security.UserDetailsImpl;
import com.sparta.board_plus.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommonResponseDTO> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDTO commentRequestDTO) {
        try {
            commentService.createComment(userDetails, commentRequestDTO);
            return ResponseEntity.ok().body(new CommonResponseDTO("댓글 작성 완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommonResponseDTO> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId, @RequestBody CommentRequestDTO commentRequestDTO) {
        try {
            commentService.updateComment(commentId, commentRequestDTO, userDetails.getUser());
            return ResponseEntity.ok().body(new CommonResponseDTO("댓글 수정 완료", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommonResponseDTO> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId) {
        try {
            commentService.deleteComment(userDetails.getUser(), commentId);
            return ResponseEntity.ok().body(new CommonResponseDTO("댓글 삭제가 완료되었습니다.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }



}
