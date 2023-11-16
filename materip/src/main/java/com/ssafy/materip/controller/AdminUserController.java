package com.ssafy.materip.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.ssafy.materip.model.dto.User;
import com.ssafy.materip.model.dto.Userlikes;
import com.ssafy.materip.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ssafy.materip.annotation.AuthRequired;
import com.ssafy.materip.model.dto.AuthDto;
import com.ssafy.materip.model.service.AuthService;
import com.ssafy.materip.util.JWTUtil;

@RestController
@RequestMapping("/admin/user")
@Api(value = "MATERIP", tags = { "User Admin Controller" })
@JsonAutoDetect
public class AdminUserController {

	@Value("${jwt.refreshtoken.expiretime}")
	private Integer refreshTokenExpireTime;

	private final UserService userService;
	private final JWTUtil jwtUtil;
	private final AuthService authService;

	public AdminUserController(UserService userService, JWTUtil jwtUtil, AuthService authService) {

		this.userService = userService;
		this.jwtUtil = jwtUtil;
		this.authService = authService;
	}
	
	
	@ApiOperation(value="로그인", notes="로그인 성공시 JWT를 반환합니다.")
	@ApiResponses({
		// https://stackoverflow.com/questions/11714485/restful-login-failure-return-401-or-custom-response
		@ApiResponse(code = 201, message = "로그인 성공"),
		@ApiResponse(code = 401, message= " 로그인 실패")
	})
	@ResponseBody
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody User user, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		User validUser = userService.login(user);
		
		// 일치하는 사용자가 없는 경우.
		if(validUser == null) {
			result.put("message", "아이디 또는 패스워드를 확인해주세요.");
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.UNAUTHORIZED);
		}
		
		// AccessToken, RefreshToken 발급
		// https://velog.io/@yaytomato/%ED%94%84%EB%A1%A0%ED%8A%B8%EC%97%90%EC%84%9C-%EC%95%88%EC%A0%84%ED%95%98%EA%B2%8C-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%B2%98%EB%A6%AC%ED%95%98%EA%B8%B0
		String accessToken = jwtUtil.createAccessToken(validUser.getId());
		String refreshToken = jwtUtil.createRefreshToken(validUser.getId());
		
		// RefreshToken은 HttpOnly Cookie로 발급
		Cookie cookie = new Cookie("refreshToken", refreshToken);
		cookie.setMaxAge(refreshTokenExpireTime);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);
		
		// 이미 발급 받은 refreshToken이 있는지 확인.
		AuthDto authDto = authService.getRefreshToken(validUser.getId());
		
		if(authDto != null) {
			authDto.setRefreshToken(refreshToken);
			authService.updateRefreshToken(authDto);
		}
		else {
			// 발급받은 refreshToken을 DB에 저장.
			authService.setRefreshToken(new AuthDto(validUser.getId(), refreshToken));
		}
		
		// AccessToken은 JSON으로 전달
		result.put("accessToken", accessToken);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.CREATED);
	}




	@ApiOperation(value="로그아웃", notes="해당 사용자의 refresh token을 삭제합니다.")
	@ApiResponses({
			// https://stackoverflow.com/questions/11714485/restful-login-failure-return-401-or-custom-response
			@ApiResponse(code = 200, message = "로그아웃 성공"),
			@ApiResponse(code = 401, message = "로그아웃 실패")
	})
	@ResponseBody
	@DeleteMapping("/logout")
	public ResponseEntity<Map<String, Object>> logout(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ParseException {
		Map<String, Object> result = new HashMap<String, Object>();
		System.out.println(request.getHeader("Authorization") + " authorization");
		System.out.println("=============================");

		String userId = jwtUtil.getUserId(request.getHeader("Authorization"));

		// RefreshToken은 HttpOnly Cookie로 발급
		Cookie cookie = new Cookie("refreshToken", "");
		cookie.setMaxAge(0);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);

		authService.updateRefreshToken(new AuthDto(userId, ""));

		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	@ApiOperation(value = "사용자 목록", notes = "등록된 모든 사용자 정보를 반환합니다.", response = List.class)
	@GetMapping()
	public ResponseEntity<?> getUserList() throws Exception {
		List<User> list = userService.getUserList();
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "회원 정보", notes = "아이디와 일치하는 사용자 정보를 반환합니다.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userid", value = "아이디", required = true, dataType = "String", paramType = "path") })
	@GetMapping(value = "/{userid}")
	public ResponseEntity<?> getUserById(@PathVariable("userid") String userId) throws Exception {
		User user = userService.getUser(userId);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@ApiOperation(value="아이디 중복 체크", notes = "등록된 사용자 중 중복되는 아이디의 수를 반환합니다.")
	@PostMapping("")
	public ResponseEntity<?> checkId(@RequestBody Map<String, String> userIdMap) throws Exception {
		int result = userService.idCheck(userIdMap.get("id"));
		System.out.println(userIdMap.toString());
//			if(result == 1) { // 중복된 아이디 존재하는 경우...
//				return new ResponseEntity<>(result, HttpStatus.CONFLICT);
//			}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "회원가입", notes = "회원 가입")
	@PostMapping(value = "/signup")
	public ResponseEntity<?> createUser(@RequestBody User user) throws Exception {
		int result = userService.joinMember(user);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "회원 정보 수정", notes = "사용자 정보를 수정합니다.")
	@PutMapping(value = "/modify")
	public ResponseEntity<?> updateUser(@RequestBody User user) throws Exception {
		int result = userService.modifyUserInfo(user);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "계정 삭제", notes = "아이디와 일치하는 사용자 정보를 삭제합니다.")
	@DeleteMapping(value = "/{userid}")
	public ResponseEntity<?> deleteUser(@RequestBody String userId) throws Exception {
		int result = userService.withdrawal(userId);
		if (result == 0) {
			return new ResponseEntity<>(result, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}


	@ApiOperation(value="마이페이지", notes="사용자 정보를 반환합니다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "사용자 정보 반환 성공"),
			@ApiResponse(code = 403, message = "권한 없음"),
			@ApiResponse(code = 404, message = "사용자 정보 없음")
	})
	@ResponseBody
	@AuthRequired
	@GetMapping("/mypage")
	public ResponseEntity<Map<String, Object>> myPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
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
		User user = userService.getUser(userId);

		// 사용자 정보 중 비밀번호는 전송하지 말아야한다.
		user.setPassword("");

		result.put("userInfo", user);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value="관리자 여부", notes="관리자 여부인지를 반환합니다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "사용자 정보 반환 성공"),
			@ApiResponse(code = 403, message = "권한 없음"),
			@ApiResponse(code = 404, message = "사용자 정보 없음")
	})
	@ResponseBody
	@AuthRequired
	@GetMapping("/isAdmin")
	public ResponseEntity<Map<String, Object>> isAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
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

		// 해당 userId의 정보를 획득.
		User user = userService.getUser(userId);

		// 사용자 정보 중 비밀번호는 전송하지 말아야한다.
		

		result.put("admin", user.getAdmin());
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "좋아요", notes = "사용자 좋아요")
	@PostMapping(value = "/{userid}/like")
	public ResponseEntity<?> likeUser(@RequestBody Userlikes userlikes) throws Exception {
		int result = userService.likeUser(userlikes.getLikedBy(), userlikes.getId());

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "좋아요 취소", notes = "사용자 좋아요 취소")
	@DeleteMapping(value = "/{userid}/unlike")
	public ResponseEntity<?> unlikeUser(@RequestBody Userlikes userlike) throws Exception {
		int result = userService.unlikeUser(userlike.getLikedBy(), userlike.getId());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "좋아요 수 확인", notes = "사용자의 좋아요 수를 반환합니다.")
	@GetMapping(value = "/{userid}/like")
	public ResponseEntity<?> getUserlikesCount(@PathVariable("userid") String userId) throws Exception {
		return new ResponseEntity<>(userService.readUserlikesCount(userId), HttpStatus.OK);
	}

	@ApiOperation(value = "좋아요 수 자세히 보기", notes = "좋아요를 누른 사용자 아이디를 모두 반환합니다.")
	@GetMapping(value = "/{userid}/like/detail")
	public ResponseEntity<?> getUserlikes(@PathVariable("userid") String userId) throws Exception {
		return new ResponseEntity<>(userService.readUserlikes(userId), HttpStatus.OK);
	}

}
