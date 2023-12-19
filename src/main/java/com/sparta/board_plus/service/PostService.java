package com.sparta.board_plus.service;

import com.sparta.board_plus.dto.PostRequestDTO;
import com.sparta.board_plus.dto.PostResponseDTO;
import com.sparta.board_plus.entity.Post;
import com.sparta.board_plus.entity.User;
import com.sparta.board_plus.entity.UserRoleEnum;
import com.sparta.board_plus.repository.PostRepository;
import com.sparta.board_plus.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    // 게시물 생성
    public void createPost(UserDetailsImpl userDetails, PostRequestDTO postRequestDTO) {
        if(postRequestDTO.getTitle().isEmpty()) {
            throw new IllegalArgumentException("제목을 입력하세요.");
        } else if(postRequestDTO.getContent().isEmpty()) {
            throw new IllegalArgumentException("내용을 입력하세요.");
        }

        postRepository.save(new Post(userDetails, postRequestDTO));
    }

    // 게시물 페이징 조회
    @Transactional(readOnly = true)
    public Page<PostResponseDTO> getPosts(User user, int page, int size, String sortBy, boolean isAsc) {
        // 페이징 처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // 사용자 권한 가져와서 ADMIN 이면 전체 조회, USER 면 본인이 추가한 부분 조회
        UserRoleEnum userRoleEnum = user.getRole();

        Page<Post> productList;

        if (userRoleEnum == UserRoleEnum.USER) {
            // 사용자 권한이 USER 일 경우
            productList = postRepository.findAllByUser(user, pageable);
        } else {
            productList = postRepository.findAll(pageable);
        }

        return productList.map(PostResponseDTO::new);
    }

    // 게시물 조회
    public PostResponseDTO getPost(Long postId) {
        Post post = findPost(postId);

        return new PostResponseDTO(post);
    }

    // 게시물 수정
    @Transactional
    public void updatePost(Long postId, PostRequestDTO postRequestDTO, UserDetailsImpl userDetails) {
        Post post = findPostUser(postId, userDetails);

        post.update(postRequestDTO);
    }

    // 게시물 삭제
    public void deletePost(Long postId, UserDetailsImpl userDetails) {
        Post post = findPostUser(postId, userDetails);

        postRepository.delete(post);
    }

    // 게시물 존재 유무 확인
    private Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));
    }

    // 게시물 존재 유무 확인 및 유저 확인
    private Post findPostUser(Long postId, UserDetailsImpl userDetails) {
        Post post = findPost(postId);

        boolean isAdmin = userDetails.getUser().getRole().equals(UserRoleEnum.ADMIN);

        if (!Objects.equals(post.getUser().getId(), userDetails.getUser().getId()) && !isAdmin) {
            throw new IllegalArgumentException("게시물 작성자만 수정 및 삭제가 가능합니다.");
        }

        return post;
    }



}
