package com.ssafy.materip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.ssafy.materip.model.dto.User;
import com.ssafy.materip.model.dto.Userlikes;
import com.ssafy.materip.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/admin/user")
@CrossOrigin("*")
@Api(value = "MATERIP", tags = {"User Admin Controller"})
@JsonAutoDetect
public class AdminUserController {

	private final UserService userService;
	
	@Autowired
	public AdminUserController(UserService userService) {
		this.userService = userService;
	}
	
	@ApiOperation(value="사용자 목록", notes = "등록된 모든 사용자 정보를 반환합니다.", response = List.class)
	@GetMapping()
	public ResponseEntity<?> getUserList() throws Exception {
		List<User> list = userService.getUserList();
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value="회원 정보", notes = "아이디와 일치하는 사용자 정보를 반환합니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userid", value="아이디", required = true, dataType = "String", paramType = "path")
	})
	@GetMapping(value="/{userid}")
	public ResponseEntity<?> getUserById(@PathVariable("userid") String userId) throws Exception {
		User user = userService.getUser(userId);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@ApiOperation(value="아이디 중복 체크", notes = "등록된 사용자 중 중복되는 아이디의 수를 반환합니다.")
	@PostMapping(value="/{userid}")
	public ResponseEntity<?> checkId(@RequestBody String userId) throws Exception {
		int result = userService.idCheck(userId);
		if(result == 1) { // 중복된 아이디 존재하는 경우...
			return new ResponseEntity<>(result, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value="회원가입", notes = "회원 가입")
	@PostMapping(value="/signup")
	public ResponseEntity<?> createUser(@RequestBody User user) throws Exception {
		int result = userService.joinMember(user);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value="회원 정보 수정", notes="사용자 정보를 수정합니다.")
	@PutMapping(value="/modify")
	public ResponseEntity<?> updateUser(@RequestBody User user) throws Exception {
		int result = userService.modifyUserInfo(user);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value="계정 삭제", notes = "아이디와 일치하는 사용자 정보를 삭제합니다.")
	@DeleteMapping(value="/{userid}")
	public ResponseEntity<?> deleteUser(@RequestBody String userId) throws Exception {
		int result = userService.withdrawal(userId);
		if(result == 0) {
			return new ResponseEntity<>(result, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value="좋아요", notes="사용자 좋아요")
	@PostMapping(value="/{userid}/like")
	public ResponseEntity<?> likeUser(@RequestBody Userlikes userlikes) throws Exception {
		int result = userService.likeUser(userlikes.getLikedBy(), userlikes.getId());
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value="좋아요 취소", notes="사용자 좋아요 취소")
	@DeleteMapping(value="/{userid}/unlike")
	public ResponseEntity<?> unlikeUser(@RequestBody Userlikes userlike) throws Exception {
		int result = userService.unlikeUser(userlike.getLikedBy(), userlike.getId());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value="좋아요 수 확인", notes="사용자의 좋아요 수를 반환합니다.")
	@GetMapping(value="/{userid}/like")
	public ResponseEntity<?> getUserlikesCount(@PathVariable("userid") String userId) throws Exception {
		return new ResponseEntity<>(userService.readUserlikesCount(userId), HttpStatus.OK);
	}
	
	@ApiOperation(value="좋아요 수 자세히 보기", notes="좋아요를 누른 사용자 아이디를 모두 반환합니다.")
	@GetMapping(value="/{userid}/like/detail")
	public ResponseEntity<?> getUserlikes(@PathVariable("userid") String userId) throws Exception {
		return new ResponseEntity<>(userService.readUserlikes(userId), HttpStatus.OK);
	}
	
}
