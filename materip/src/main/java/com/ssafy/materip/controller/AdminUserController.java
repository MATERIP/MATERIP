package com.ssafy.materip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.materip.model.dto.User;
import com.ssafy.materip.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/admin/user")
@CrossOrigin("*")
@Api(value = "MATERIP", tags = {"User Admin Controller"})
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
}
