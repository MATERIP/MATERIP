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
	
	List<Board> readAllReview() throws SQLException;
	
	List<Board> readAllRecruitment() throws SQLException;
	
	Board readBoardById(int boardId) throws SQLException;
	
	List<Board> readMateBoardsByUserId(String userId) throws SQLException;
	List<Board> readReviewBoardsByUserId(String userId) throws SQLException;
	
	int updateBoard(Board board) throws SQLException;

	int deleteBoard(int sequence) throws SQLException;

	void updateBoardHits(int boardId) throws SQLException;
}
