package com.sparta.board_plus.repository;

import com.sparta.board_plus.entity.Comment;
import com.sparta.board_plus.entity.Post;
import com.sparta.board_plus.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByUser(User user, Pageable pageable);
}
