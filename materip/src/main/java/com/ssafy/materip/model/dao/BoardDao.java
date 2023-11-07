package com.ssafy.materip.model.dao;

import com.ssafy.materip.model.dto.Board;
import com.ssafy.materip.model.dto.Comments;
import com.ssafy.materip.model.dto.Participants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.jdbc.SQL;

import javax.xml.stream.events.Comment;
import java.sql.SQLException;
import java.util.List;

@Mapper
public interface BoardDao {

    int createBoard(Board board) throws SQLException;

    List<Board> readAllBoards() throws SQLException;

    int updateBoard(Board board) throws  SQLException;

    int deleteBoard(Board board) throws SQLException;









}
