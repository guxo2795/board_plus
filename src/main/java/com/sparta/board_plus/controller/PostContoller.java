package com.sparta.board_plus.controller;


import com.sparta.board_plus.dto.CommonResponseDTO;
import com.sparta.board_plus.dto.PostRequestDTO;
import com.sparta.board_plus.dto.PostResponseDTO;
import com.sparta.board_plus.security.UserDetailsImpl;
import com.sparta.board_plus.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostContoller {
    private final PostService postService;

    // 게시물 생성
    @PostMapping
    public ResponseEntity<CommonResponseDTO> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostRequestDTO postRequestDTO) {
        try {
            postService.createPost(userDetails, postRequestDTO);
            return ResponseEntity.ok().body(new CommonResponseDTO("게시물 등록 완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 관심 상품 조회하기
    @GetMapping
    public Page<PostResponseDTO> getPosts(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 응답 보내기
        return postService.getPosts(userDetails.getUser(),
                page-1, size, sortBy, isAsc);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<CommonResponseDTO> getPost(@PathVariable Long postId) {
        try {
            return ResponseEntity.ok().body(postService.getPost(postId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<CommonResponseDTO> updatePost(@PathVariable Long postId, @RequestBody PostRequestDTO postRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            postService.updatePost(postId, postRequestDTO, userDetails);
            return ResponseEntity.ok().body(new CommonResponseDTO("게시물 수정 완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<CommonResponseDTO> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            postService.deletePost(postId, userDetails);
            return ResponseEntity.ok().body(new CommonResponseDTO("게시물 삭제 완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }


}
