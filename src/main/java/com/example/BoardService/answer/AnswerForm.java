package com.example.BoardService.answer;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

//Dto for Answer
@Data
public class AnswerForm {
    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;
}
