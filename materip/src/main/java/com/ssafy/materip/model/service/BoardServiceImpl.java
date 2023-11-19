package com.ssafy.materip.model.service;

import com.ssafy.materip.model.dao.BoardDao;
import com.ssafy.materip.model.dao.CommentsDao;
import com.ssafy.materip.model.dao.ParticipantsDao;
import com.ssafy.materip.model.dto.Board;
import com.ssafy.materip.model.dto.Comments;
import com.ssafy.materip.model.dto.Participants;

import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

	private final BoardDao boardDao;
	private final CommentsDao commentDao;
	private final ParticipantsDao participantsDao;

	public BoardServiceImpl(BoardDao boardDao, CommentsDao commentDao, ParticipantsDao participantsDao) {
		super();
		this.boardDao = boardDao;
		this.commentDao = commentDao;
		this.participantsDao = participantsDao;
	}

	@Override
	public int writeBoard(Board board) throws Exception {
		board.setModifiedAt(Timestamp.valueOf(LocalDateTime.now()));
		board.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
		System.out.println(board.toString());
		boardDao.createBoard(board);

		return 0;
	}

	@Override
	public int editBoard(Board board) throws Exception {
		boardDao.updateBoard(board);
		return 0;
	}

	@Override
	public List<Board> getBoardList() throws Exception {

		return boardDao.readAllBoards();
	}
	
	@Override
	public List<Board> getReviewList() throws Exception {
		return boardDao.readAllReview();
	}
	
	
	@Override
	public List<Board> getRecruitmentList() throws Exception {
		return boardDao.readAllRecruitment();
	}
	
	@Override
	public Board getBoardById(int boardId) throws Exception {
		// TODO Auto-generated method stub
		return boardDao.readBoardById(boardId);
	}

	@Override
	public int deleteBoard(int board_id) throws Exception {
		// TODO Auto-generated method stub
		boardDao.deleteBoard(board_id);
		return 0;
	}
	
	@Override
	public void updateBoardHits(int boardId) throws Exception {
		boardDao.updateBoardHits(boardId);
	}

	@Override
	public List<Board> getReviewListById(String userId) throws Exception {
		return boardDao.readReviewBoardsByUserId(userId);
	}

	@Override
	public List<Board> getMateListById(String userId) throws Exception {
		return boardDao.readMateBoardsByUserId(userId);
	}

	@Override
	public int writeComment(Comments comments, int article_id) throws Exception {
		comments.setArticleId(article_id);
		comments.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
		comments.setModifiedAt(Timestamp.valueOf(LocalDateTime.now()));
		return commentDao.createComments(comments);
	}

	@Override
	public int removeComment(int sequence) throws Exception {
		commentDao.deleteComments(sequence);
		return 0;
	}

	@Override
	public int modifyComment(Comments comments) throws Exception {
		comments.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
		comments.setModifiedAt(Timestamp.valueOf(LocalDateTime.now()));
		commentDao.updateComments(comments);
		return 0;
	}

	@Override
	public List<Comments> getCommentList(int board_id) throws SQLException {

		return commentDao.readAllComments(board_id);
	}

	@Override
	public int getCommentCnt(int board_id) {
		// TODO Auto-generated method stub
		return commentDao.readCommentsCnt(board_id);
	}

	// Participants
	
	@Override
	public int join(Participants participants) throws Exception {
		return participantsDao.addParticipants(participants);
	}

	@Override
	public int getParticipantsCount(int boardId) throws Exception {
		return participantsDao.getParticipantsCnt(boardId);
	}

	@Override
	public List<Participants> getParticipantsList(int boardId) throws Exception {
		return participantsDao.getParticipants(boardId);
	}

	@Override
	public int leave(Participants participants) throws Exception {
		return participantsDao.removeParticipants(participants);
	}

}
