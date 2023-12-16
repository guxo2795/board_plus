package com.sparta.board_plus.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
    @Pattern(regexp = "[A-za-z0-9]{3,10}$")
    private String username;
    @Pattern(regexp = "[A-Za-z\\d~!@#$%^&*()+|=]{4,15}$")
    private String password;

    private String passwordCheck;

    private String email;

    private boolean admin = false;

    private String admintoken = "";

}
