package com.ssafy.materip.model.dao;

import com.ssafy.materip.model.dto.Comments;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface CommentsDao {
	
	int createComments(Comments comments) throws SQLException;

	List<Comments> readAllComments(int board_id) throws SQLException;

	int updateComments(Comments comments) throws SQLException;

	int deleteComments(int sequence) throws SQLException;

	int readCommentsCnt(int board_id);
	
	Comments readComments(int sequence) throws SQLException;
	
	int deleteAllComments(int boardId) throws SQLException;
}
