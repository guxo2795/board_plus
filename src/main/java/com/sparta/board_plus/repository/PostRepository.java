package com.sparta.board_plus.repository;

import com.sparta.board_plus.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
