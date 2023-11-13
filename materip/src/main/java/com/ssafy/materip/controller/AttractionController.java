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
import com.ssafy.materip.model.dao.AttractionInfoDao;
import com.ssafy.materip.model.dto.Board;
import com.ssafy.materip.model.dto.Comments;
import com.ssafy.materip.model.dto.User;
import com.ssafy.materip.model.service.AttractionInfoService;
import com.ssafy.materip.model.service.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/attraction")
@CrossOrigin("*")
@Api(value = "MATERIP", tags = { "Attraction Controller" })
@JsonAutoDetect
public class AttractionController {
	private final AttractionInfoService attractionInfoService;

	
	@Autowired
	public AttractionController(AttractionInfoService attractionInfoService) {
		super();
		this.attractionInfoService = attractionInfoService;
	}

	@ApiOperation(value = "보드 상세 정보", notes="보드 아이디로 보드 상세 정보를 반환힙니다.")
	@GetMapping("/info")
	public ResponseEntity<?> getBoardById() throws Exception {
		
		return new ResponseEntity<>(attractionInfoService.getTravelNameList(), HttpStatus.OK);
	}
	
}
