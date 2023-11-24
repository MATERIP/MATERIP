package com.ssafy.materip.model.service;

import com.ssafy.materip.model.dao.BoardDao;
import com.ssafy.materip.model.dao.CommentsDao;
import com.ssafy.materip.model.dao.ParticipantsDao;
import com.ssafy.materip.model.dto.Board;
import com.ssafy.materip.model.dto.Comments;
import com.ssafy.materip.model.dto.Participants;

import org.springframework.dao.DuplicateKeyException;
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
		
		if(board.boardType.equals("recruitment")) { // 모집글인 경우...
			// 방금 쓴 글의 아이디 가져오기
			List<Board> myMateList =  boardDao.readMateBoardsByUserId(board.author);
			// 모집글 작성자를 참여자로 추가하고 승인
			Participants participants = new Participants();
			participants.setBoardId(myMateList.get(0).id);
			participants.setUserId(board.author);
			participants.setStatus(1);
			participantsDao.addParticipants(participants);
			participantsDao.accept(participants);
		}
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
	public int deleteBoard(int boardId) throws Exception {

		// 참여 중인 모든 참여자 삭제
		participantsDao.removeAllParticipants(boardId);
		// 게시판의 모든 댓글 삭제
		commentDao.deleteAllComments(boardId);

		boardDao.deleteBoard(boardId);
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
	public int writeComment(Comments comments) throws Exception {
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
	
	@Override
	public Comments getComment(int sequence) throws Exception {
		return commentDao.readComments(sequence);
	}

	// Participants
	
	@Override
	public int join(Participants participants) throws Exception {
		Board board = boardDao.readBoardById(participants.boardId);
		
		if(board.currentCount < board.maxCount && !board.author.equals(participants.userId)) {
//			boardDao.increaseBoardCurrentCount(participants.boardId);
			return participantsDao.addParticipants(participants);
		}
		return -1;
//		return participantsDao.addParticipants(participants);
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
		Board board = boardDao.readBoardById(participants.boardId);
		
		System.out.println(board.toString());
		
		if(board.currentCount >= 1 && !board.author.equals(participants.userId)) {
//			boardDao.decreaseBoardCurrentCount(participants.boardId);
			return participantsDao.removeParticipants(participants);
		}
		System.out.println(board.toString());
		
		return -1;
//		return participantsDao.removeParticipants(participants);
	}

	@Override
	public boolean isJoinable(Participants participants) throws Exception {
		// 1인 경우...게시판 아이디, 사용자 아이디와 일치하는 참여자가 이미 존재 
		int result = participantsDao.isJoinable(participants);
		return result == 0;
	}

	@Override
	public List<Participants> getAllParticipantsList(int boardId) throws Exception {
		return participantsDao.getAllParticipants(boardId);
	}

	@Override
	public int accept(Participants participants) throws Exception {
		boardDao.increaseBoardCurrentCount(participants.getBoardId());
		return participantsDao.accept(participants);
	}

	@Override
	public int decline(Participants participants) throws Exception {
		
		return participantsDao.decline(participants);
	}


}