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


    int createComments(Comments comments) throws SQLException;

    List<Comments> readAllComments(Comments comments) throws SQLException;

    int updateComments(Comments comments) throws SQLException;

    int deleteComments(Comments comments) throws SQLException;


    int addParticipants(Participants participants) throws SQLException;
    List<Participants> getParticipants(String board_id) throws SQLException;
    int removeParticipants(Participants participants) throws SQLException;
    int getParticipantsCnt(String board_id) throws  SQLException;








}
