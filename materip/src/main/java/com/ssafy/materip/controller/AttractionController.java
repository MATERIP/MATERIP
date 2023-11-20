package com.ssafy.materip.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.ssafy.materip.model.dto.AttractionDetail;
import com.ssafy.materip.model.service.AttractionDescriptionService;
import com.ssafy.materip.model.service.AttractionInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/attraction")
@Api(value = "MATERIP", tags = { "Attraction Controller" })
@JsonAutoDetect
public class AttractionController {
	private final AttractionInfoService attractionInfoService;
	private final AttractionDescriptionService attractionDescriptionService;
	
	@Autowired
	public AttractionController(AttractionInfoService attractionInfoService, AttractionDescriptionService attractionDescriptionService) {
		
		super();
		this.attractionInfoService = attractionInfoService;
		this.attractionDescriptionService = attractionDescriptionService;
	}

	@ApiOperation(value = "여행지 제목 리스트", notes="모든 여행지 제목들을 반환합니다.")
	@GetMapping("/title")
	public ResponseEntity<?> getTitle() throws Exception {
		
		return new ResponseEntity<>(attractionInfoService.getTravelNameList(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "여행지 제목 리스트", notes="모든 여행지 제목들을 반환합니다.")
	@GetMapping("/info/{contentId}")
	public ResponseEntity<?> getAttractionInfoById(@PathVariable("contentId") int contentId) throws Exception {
		Map<String, Object> result= new HashMap<String, Object>();
		result.put("info", attractionInfoService.getAttractionInfo(contentId));
		System.out.println(attractionInfoService.getAttractionInfo(contentId));
		result.put("description", attractionDescriptionService.getAttractionDescription(contentId));
		System.out.println(attractionDescriptionService.getAttractionDescription(contentId));
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "여행지 검색 정보", notes="여행지 검색정보를 반환힙니다.")
	@GetMapping("/searchinfo/{title}")
	public ResponseEntity<?> searchByTitle(@PathVariable("title") String title) throws Exception {
		
		return new ResponseEntity<>(attractionInfoService.getTravelNameSearchList(title), HttpStatus.OK);
	}
	
	
	
}
