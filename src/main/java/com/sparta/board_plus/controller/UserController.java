package com.sparta.board_plus.controller;

import com.sparta.board_plus.dto.CommonResponseDTO;
import com.sparta.board_plus.dto.UserRequestDTO;
import com.sparta.board_plus.repository.UserRepository;
import com.sparta.board_plus.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDTO> signup(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        try {
            userService.signup(userRequestDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(new CommonResponseDTO("회원가입 성공", HttpStatus.CREATED.value()));
    }


}
