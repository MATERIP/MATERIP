package com.ssafy.materip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.ssafy.materip.model.service.AttractionInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/attraction")
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
	
	@ApiOperation(value = "보드 상세 정보", notes="보드 아이디로 보드 상세 정보를 반환힙니다.")
	@GetMapping("/searchinfo/{title}")
	public ResponseEntity<?> searchByTitle(String title) throws Exception {
		
		return new ResponseEntity<>(attractionInfoService.getTravelNameSearchList(title), HttpStatus.OK);
	}
	
}
