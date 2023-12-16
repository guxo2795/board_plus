package com.sparta.board_plus.service;

import com.sparta.board_plus.dto.UserRequestDTO;
import com.sparta.board_plus.entity.User;
import com.sparta.board_plus.entity.UserRoleEnum;
import com.sparta.board_plus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private static PasswordEncoder passwordEncoder;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    public void signup(UserRequestDTO userRequestDTO) {
        String username = userRequestDTO.getUsername();
        String password = passwordEncoder.encode(userRequestDTO.getPassword());
        String email = userRequestDTO.getEmail();
        UserRoleEnum role = UserRoleEnum.USER;

        // 회원 중복 확인
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 비밀번호 확인
        String passwordCheck = userRequestDTO.getPasswordCheck();
        if(userRequestDTO.getPassword().contains(username)){
            throw new IllegalArgumentException("비밀번호는 닉네임을 포함할 수 없습니다.");
        } else if(!userRequestDTO.getPassword().equals(passwordCheck)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // email 중복 확인
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if(checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 email이 존재합니다.");
        }

        // 사용자 권한 확인
        if(userRequestDTO.isAdmin()){
            if(!ADMIN_TOKEN.equals(userRequestDTO.getAdmintoken())) {
                throw new IllegalArgumentException("관리자 암호가 틀립니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 유저 등록
        User user = new User(username, password, email, role);
        userRepository.save(user);
    }
}
