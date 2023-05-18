package com.library.mapper;

import com.library.entity.Board;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapperInterface {
    @Select("select * from boards")
    List<Board> SelectAll();

    @Insert("insert into boards(writer, subject, description) values(#{board.writer}, #{board.subject}, #{board.description})")
    int Insert(@Param("board") final Board bean);

    @Select("select * from boards where no = #{no}")
        // 게시물 상세보기
    Board SelectOne(@Param("no") Integer no);

    @Update("update boards set writer=#{board.writer}, subject=#{board.subject}, description=#{board.description} where no=#{board.no} ")
        // 게시물 수정하기
    int Update(@Param("board") final Board bean);

    @Delete("delete from boards where no = #{no}")
        // 게시물 삭제하기
    int Delete(@Param("no") Integer no);
}
