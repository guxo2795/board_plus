package com.sparta.board_plus.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponseDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;
}
