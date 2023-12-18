package com.sparta.board_plus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BoardPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardPlusApplication.class, args);
    }

}
