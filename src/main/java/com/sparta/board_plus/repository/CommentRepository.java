package com.sparta.board_plus.repository;

import com.sparta.board_plus.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
