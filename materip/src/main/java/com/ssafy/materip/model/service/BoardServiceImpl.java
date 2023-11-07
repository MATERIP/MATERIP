package com.ssafy.materip.model.service;

import com.ssafy.materip.model.dao.BoardDao;
import com.ssafy.materip.model.dto.Board;
import com.ssafy.materip.model.dto.Comments;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardDao boardDao;

    public BoardServiceImpl(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    @Override
    public int writeBoard(Board board) throws Exception {
        return 0;
    }

    @Override
    public int editBoard(Board board) throws Exception {
        return 0;
    }

    @Override
    public int deleteBoard(Board board) throws Exception {
        return 0;
    }

    @Override
    public List<Board> getBoardList() throws Exception {
        return null;
    }

    @Override
    public int writeComment(Comments comments, String board_id) throws Exception {
        return 0;
    }

    @Override
    public int removeComment(Comments comments) throws Exception {
        return 0;
    }

    @Override
    public int modifyComment(Comments comments) throws Exception {
        return 0;
    }

    @Override
    public List<Comments> getCommentList(String board_id) {
        return null;
    }

    @Override
    public int getCommentCntByBoardId(String board_id) {
        return 0;
    }

    @Override
    public int addParticipants(String board_id, String user_id) {
        return 0;
    }

    @Override
    public int getParticipantsCnt(String board_id) {
        return 0;
    }
}
