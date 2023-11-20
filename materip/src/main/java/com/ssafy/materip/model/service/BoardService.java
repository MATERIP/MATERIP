package com.ssafy.materip.model.service;

import com.ssafy.materip.model.dto.Board;
import com.ssafy.materip.model.dto.Comments;
import com.ssafy.materip.model.dto.Participants;

import java.util.List;

public interface BoardService {

	// board write
	int writeBoard(Board board) throws Exception;

	// board edit
	int editBoard(Board board) throws Exception;

	// board remove
	int deleteBoard(int boardId) throws Exception;

	// get Board List
	List<Board> getBoardList() throws Exception;
	
	// get Review List
	List<Board> getReviewList() throws Exception;
	
	// get Recruitment List
	List<Board> getRecruitmentList() throws Exception;


	
	// get Board By Id
	Board getBoardById(int boardId) throws Exception; 
	
	// update Board hits by Id
	void updateBoardHits(int boardId) throws Exception;
	
	List<Board> getReviewListById(String userId) throws Exception;

	List<Board> getMateListById(String userId) throws Exception;
	// Comment

	// add comment
	int writeComment(Comments comments, int article_id) throws Exception;

	// remove comment
	int removeComment(int sequence) throws Exception;

	// modify comment
	int modifyComment(Comments comments) throws Exception;

	// get comment list
	List<Comments> getCommentList(int board_id) throws Exception;
	
	// get comment count
	int getCommentCnt(int board_id);
	
	// Participants
	
	// join
	int join(Participants participants) throws Exception;

	// get particpants count
	int getParticipantsCount(int boardId) throws Exception;
	
	// get particpants list
	List<Participants> getParticipantsList(int boardId) throws Exception;
	
	// leave
	int leave(Participants participants) throws Exception;

	boolean isJoinable(Participants participants) throws Exception;
}
