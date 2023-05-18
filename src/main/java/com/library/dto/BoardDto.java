package com.library.dto;

import com.library.entity.Board;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardDto {
    private  String boardData ; // 등록일자

    public BoardDto(Board board) {
        String pattern = "yyyy-MM-DD HH:MM" ;

    }


}
