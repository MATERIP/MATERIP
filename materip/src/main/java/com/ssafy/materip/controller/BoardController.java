package com.ssafy.materip.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.ssafy.materip.annotation.AuthRequired;
import com.ssafy.materip.model.dto.Board;
import com.ssafy.materip.model.dto.Comments;
import com.ssafy.materip.model.dto.Participants;
import com.ssafy.materip.model.dto.User;
import com.ssafy.materip.model.service.AuthService;
import com.ssafy.materip.model.service.BoardService;
import com.ssafy.materip.model.service.UserService;
import com.ssafy.materip.util.JWTUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/board")
@Api(value = "MATERIP", tags = { "Board Controller" })
@JsonAutoDetect
public class BoardController {
	private final BoardService boardService;
	@Value("${jwt.refreshtoken.expiretime}")
	private Integer refreshTokenExpireTime;

	private final JWTUtil jwtUtil;
	private final AuthService authService;
	private final UserService userService;
	
	public BoardController(BoardService boardService, JWTUtil jwtUtil, AuthService authService, UserService userService) {

		this.boardService = boardService;
		this.jwtUtil = jwtUtil;
		this.authService = authService;
		this.userService = userService;
	}
	

	@ApiOperation(value = "보드 상세 정보", notes="보드 아이디로 보드 상세 정보를 반환힙니다.")
	@GetMapping("/detail/{board_id}")
	@AuthRequired
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getBoardById(@PathVariable("board_id") int board_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		// Authorization에 포함된 accessToken의 userId를 가져온다.
		String userId = (request.getHeader("Authorization") == null) || request.getHeader("Authorization").equals("") ? 
				"" : jwtUtil.getUserId(request.getHeader("Authorization"));
		
		
		
		boardService.updateBoardHits(board_id); // 게시판 조회 시 조회수 1 증가
		Board board = boardService.getBoardById(board_id);
		
		result.put("board", board);
		result.put("auth", userId.equals(board.author) || userService.getUser(userId).admin == 1);
		
		System.out.println(result.toString());
		
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
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
	
	
	@ApiOperation(value="사용자별 리뷰 보드 얻기", notes="접속한 사용자의 보드들을 얻어옵니다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "보드 정보 반환 성공"),
			@ApiResponse(code = 403, message = "권한 없음"),
			@ApiResponse(code = 404, message = "사용자 정보 없음")
	})
	@ResponseBody
	@AuthRequired
	@GetMapping("/myboard/review")
	public ResponseEntity<Map<String, Object>> getReviewBoardbyId(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
	
		// Authorization에 포함된 accessToken의 userId를 가져온다.
		String userId = jwtUtil.getUserId(request.getHeader("Authorization"));

		Cookie[] cookieList = request.getCookies();

		String requestRefreshToken = "";

		for(Cookie cookie : cookieList) {
			if(cookie.getName().equals("refreshToken")) {
				requestRefreshToken = cookie.getValue();
			}
		}

		// 요청으로 들어온 refreshToken이 Database의 refreshToken과 일치하는지 확인.
		String dbRefreshToken = authService.getRefreshToken(userId).getRefreshToken();

		if(!requestRefreshToken.equals(dbRefreshToken)) {
			// RefreshToken은 HttpOnly Cookie로 발급
			Cookie cookie = new Cookie("refreshToken", "");
			cookie.setMaxAge(0);
			cookie.setHttpOnly(true);
			cookie.setPath("/");
			System.out.println("토큰이 다름");
			response.addCookie(cookie);

			result.put("message", "로그인이 필요합니다.");
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.FORBIDDEN);
		}

		// 해당 userId의 정보를 획득.
		
		List<Board> boardList = boardService.getReviewListById(userId);

		

		result.put("boardList", boardList);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}



	@ApiOperation(value="사용자별 메이트 보드 얻기", notes="접속한 사용자의 보드들을 얻어옵니다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "보드 정보 반환 성공"),
			@ApiResponse(code = 403, message = "권한 없음"),
			@ApiResponse(code = 404, message = "사용자 정보 없음")
	})
	@ResponseBody
	@AuthRequired
	@GetMapping("/myboard/mate")
	public ResponseEntity<Map<String, Object>> getMateBoardbyId(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();

		// Authorization에 포함된 accessToken의 userId를 가져온다.
		String userId = jwtUtil.getUserId(request.getHeader("Authorization"));

		Cookie[] cookieList = request.getCookies();

		String requestRefreshToken = "";

		for(Cookie cookie : cookieList) {
			if(cookie.getName().equals("refreshToken")) {
				requestRefreshToken = cookie.getValue();
			}
		}

		// 요청으로 들어온 refreshToken이 Database의 refreshToken과 일치하는지 확인.
		String dbRefreshToken = authService.getRefreshToken(userId).getRefreshToken();

		if(!requestRefreshToken.equals(dbRefreshToken)) {
			// RefreshToken은 HttpOnly Cookie로 발급
			Cookie cookie = new Cookie("refreshToken", "");
			cookie.setMaxAge(0);
			cookie.setHttpOnly(true);
			cookie.setPath("/");
			System.out.println("토큰이 다름");
			response.addCookie(cookie);

			result.put("message", "로그인이 필요합니다.");
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.FORBIDDEN);
		}

		// 해당 userId의 정보를 획득.

		List<Board> boardList = boardService.getMateListById(userId);

		result.put("boardList", boardList);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}







	@ApiOperation(value="관리자 여부", notes="관리자 여부인지를 반환합니다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "사용자 정보 반환 성공"),
			@ApiResponse(code = 403, message = "권한 없음"),
			@ApiResponse(code = 404, message = "사용자 정보 없음")
	})
	@ResponseBody
	@GetMapping("/modifyAuth")
	// board list => list중에 하나 클릭하면 => boardid 넘어가고 => boardid넘어가면 boardid board detail + modify?
	public ResponseEntity<Map<String, Object>> modifyAuth(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		// Authorization에 포함된 accessToken의 userId를 가져온다.
		String userId = request.getHeader("Authorization").equals("")? "":jwtUtil.getUserId(request.getHeader("Authorization"));

		Cookie[] cookieList = request.getCookies();

		String requestRefreshToken = "";

		for(Cookie cookie : cookieList) {
			if(cookie.getName().equals("refreshToken")) {
				requestRefreshToken = cookie.getValue();
			}
		}
		System.out.println(requestRefreshToken);
		// 요청으로 들어온 refreshToken이 Database의 refreshToken과 일치하는지 확인.
		String dbRefreshToken = authService.getRefreshToken(userId).getRefreshToken();

		if(!requestRefreshToken.equals(dbRefreshToken)) {
			// RefreshToken은 HttpOnly Cookie로 발급
			Cookie cookie = new Cookie("refreshToken", "");
			cookie.setMaxAge(0);
			cookie.setHttpOnly(true);
			cookie.setPath("/");
			System.out.println("토큰이 다름");
			response.addCookie(cookie);

			result.put("message", "로그인이 필요합니다.");
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.FORBIDDEN);
		}

		int boardId = Integer.parseInt(request.getParameter("board-id"));
		Board board = boardService.getBoardById(boardId);
		result.put("boardDetail", board);
		result.put("auth",board.author.equals(userId));

		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
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

	@ApiOperation(value = "댓글 리스트", notes = "보드 아이디에 따라 댓글 리스트를 불러옵니다")
	@GetMapping("/comment/getList/{board_id}")
	public ResponseEntity<?> getCommentList(@PathVariable("board_id") int board_id) throws Exception {
		List<Comments> result = boardService.getCommentList(board_id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "댓글 개수", notes = "보드 아이디에 따른 댓글 개수를 불러옵니다.")
	@GetMapping("/comment/getCommentCnt/{board_id}")
	public ResponseEntity<?> getCommentCnt(@PathVariable("board_id") int board_id) throws Exception {
		int result = boardService.getCommentCnt(board_id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "댓글 작성", notes = "댓글 작성합니다.")
	@PostMapping("/comment/write")
	public ResponseEntity<?> writeComment(@RequestBody Comments comments) throws Exception {
		int result = boardService.writeComment(comments);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "댓글 수정", notes = "댓글 수정합니다.")
	@PutMapping("/comment/modify")
	public ResponseEntity<?> modifyComment(@RequestBody Comments comments) throws Exception {
		int result = boardService.modifyComment(comments);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제합니다.")
	@DeleteMapping("/comment/delete")
	public ResponseEntity<?> deleteComment(@RequestParam(value="sequence") int sequence) throws Exception {
		int result = boardService.removeComment(sequence);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "댓글 조회", notes = "댓글을 조회합니다.")
	@GetMapping("/comment/detail")
	public ResponseEntity<?> viewComment(@RequestParam(value = "sequence") int sequence) throws Exception {
		Comments comment = boardService.getComment(sequence);
		return new ResponseEntity<>(comment, HttpStatus.OK);
	}
	
	@ApiOperation(value = "조회수 증가", notes = "게시판 조회수 증가")
	@GetMapping("/updateHits/{board_id}")
	public ResponseEntity<?> updateHits(@PathVariable("board_id") int board_id) throws Exception {
		boardService.updateBoardHits(board_id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "모집글 참가", notes = "모집글 게시판에 참가합니다.")
	@PostMapping("/participants/join")
	public ResponseEntity<?> join(@RequestBody Participants participants) throws Exception {
		
		int result = boardService.join(participants);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value ="모집글 참가 취소", notes = "모집글 게시판 참가를 취소합니다.")
	@PostMapping("/participants/leave")
	public ResponseEntity<?> leave(@RequestBody Participants participants) throws Exception {
		int result = boardService.leave(participants);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "모집글 참여인원 조회", notes = "모집글에 참여중인 인원 수를 반환합니다.")
	@GetMapping("/participants/{board_id}")
	public ResponseEntity<?> readCount(@PathVariable("board_id") int boardId) throws Exception {
		int result = boardService.getParticipantsCount(boardId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "모집글 참여인원 상세 조회", notes = "모집글에 참여중인 인원의 모든 사용자 아이디 목록을 반환합니다.")
	@GetMapping("/participants/{board_id}/detail")
	public ResponseEntity<Map<String, Object>> readDetail(@PathVariable("board_id") int boardId) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>(); 
		result.put("userList", boardService.getParticipantsList(boardId));
		return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "모집글 참여 가능 여부 조회", notes = "모집글에 참여 가능 여부를 반환합니다.")
	@GetMapping("/participants/{board_id}/isjoinable")
	public ResponseEntity<?> readJoinable(@PathVariable("board_id") int boardId, @RequestParam(value="userid") String userId) throws Exception {
		Participants participants = new Participants();
		participants.setUserId(userId);
		participants.setBoardId(boardId);
		
		boolean result = boardService.isJoinable(participants);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}