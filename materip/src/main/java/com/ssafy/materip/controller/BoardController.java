package com.ssafy.materip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.ssafy.materip.model.dto.Board;
import com.ssafy.materip.model.dto.Comments;
import com.ssafy.materip.model.dto.User;
import com.ssafy.materip.model.service.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/board")
@Api(value = "MATERIP", tags = { "Board Controller" })
@JsonAutoDetect
public class BoardController {
	private final BoardService boardService;

	@Autowired
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}

	@ApiOperation(value = "보드 상세 정보", notes="보드 아이디로 보드 상세 정보를 반환힙니다.")
	@GetMapping("/detail/{board_id}")
	public ResponseEntity<?> getBoardById(@PathVariable("board_id") int board_id) throws Exception {
		Board board = boardService.getBoardById(board_id);
		return new ResponseEntity<>(board, HttpStatus.OK);
	}
	
	@ApiOperation(value = "보드 목록", notes = "등록된 모든 보드 정보를 반환합니다.", response = List.class)
	@GetMapping("/getList")
	public ResponseEntity<?> getBoardList() throws Exception {
		List<Board> list = boardService.getBoardList();
		return new ResponseEntity<List<Board>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "리뷰 목록", notes = "등록된 모든 리뷰 정보를 반환합니다.", response = List.class)
	@GetMapping("/getReviewList")
	public ResponseEntity<?> getReviewList() throws Exception {
		List<Board> list = boardService.getReviewList();
		return new ResponseEntity<List<Board>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "모집글 목록", notes = "등록된 모든 모집글 정보를 반환합니다.", response = List.class)
	@GetMapping("/getRecruitmentList")
	public ResponseEntity<?> getRecruitmentList() throws Exception {
		List<Board> list = boardService.getRecruitmentList();
		return new ResponseEntity<List<Board>>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "보드 수정", notes = "보드를 수정합니다.")
	@PutMapping(value = "/modify")
	public ResponseEntity<?> modifyBoard(@RequestBody Board board) throws Exception {
		System.out.println(board.toString());
		int result = boardService.editBoard(board);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "보드 작성", notes = "보드를 작성합니다.")
	@PostMapping(value = "/write")
	public ResponseEntity<?> writeBoard(@RequestBody Board board) throws Exception {
		System.out.println(board.toString());
		int result = boardService.writeBoard(board);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "보드 제거", notes = "보드 아이디에 따라 보드 삭제를 진행합니다", response = Integer.class)
	@DeleteMapping("/delete/{board_id}")
	public ResponseEntity<?> deleteBoard(@PathVariable("board_id") int board_id) throws Exception {
		int result = boardService.deleteBoard(board_id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "댓글 리스트", notes = "보드 아이디에 따라 댓글 리스트를 불러옵니다", response = Comments.class)
	@GetMapping("/comment/getList/{board_id}")
	public ResponseEntity<?> getCommentList(@PathVariable("board_id") int board_id) throws Exception {
		List<Comments> result = boardService.getCommentList(board_id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "댓글 개수", notes = "보드 아이디에 따른 댓글 개수를 불러옵니다.", response = Integer.class)
	@GetMapping("/comment/getCommentCnt/{board_id}")
	public ResponseEntity<?> getCommentCnt(@PathVariable("board_id") int board_id) throws Exception {
		int result = boardService.getCommentCnt(board_id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "댓글 작성", notes = "댓글 작성합니다. ", response = Integer.class)
	@PostMapping("/comment/write")
	public ResponseEntity<?> writeComment(@RequestBody Comments comments, int board_id) throws Exception {
		int result = boardService.writeComment(comments, board_id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "댓글 수정", notes = "댓글 수정합니다.", response = Integer.class)
	@PostMapping("/comment/modify")
	public ResponseEntity<?> modifyComment(@RequestBody Comments comments) throws Exception {
		int result = boardService.modifyComment(comments);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제합니다.", response = Integer.class)
	@DeleteMapping("/comment/delete/{sequence}")
	public ResponseEntity<?> deleteComment(@PathVariable("sequence") int sequence) throws Exception {
		int result = boardService.removeComment(sequence);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "조회수 증가", notes = "게시판 조회수 증가")
	@GetMapping("/updateHits/{board_id}")
	public ResponseEntity<?> updateHits(@PathVariable("board_id") int board_id) throws Exception {
		boardService.updateBoardHits(board_id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
