package com.ssafy.materip.controller;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.ssafy.materip.model.dto.AttractionDetail;
import com.ssafy.materip.model.service.AttractionDescriptionService;
import com.ssafy.materip.model.dto.AttractionInfo;
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


	@ApiOperation(value = "여행지 전체 이름 목록", notes="여행지 전체 이름 목록을 반환합니다.")
	@GetMapping("/info")
	public ResponseEntity<?> getTravelTitleList() throws Exception {
		return new ResponseEntity<>(attractionInfoService.getTravelNameList(), HttpStatus.OK);
	}
	

	@ApiOperation(value = "여행지 이름으로 검색", notes="여행지 이름으로 여행지 상세 정보를 반환합니다.")
	@GetMapping("/searchinfo/{title}")
	public ResponseEntity<?> searchByTitle(@PathVariable("title") String title) throws Exception {
		
		return new ResponseEntity<>(attractionInfoService.getTravelNameSearchList(title), HttpStatus.OK);
	}
	

	@ApiOperation(value = "여행지 전체 상세 목록", notes="여행지 전체 상세 정보를 반환합니다.")
	@GetMapping("/info/detail")
	public ResponseEntity<?> getTravelList() throws Exception {
		return new ResponseEntity<>(attractionInfoService.getTravelList(), HttpStatus.OK);
	}

	
	@ApiOperation(value = "시도 코드로 여행지 검색", notes="시도 코드에 해당하는 여행지 정보를 반환합니다.")
	@GetMapping("/info/region1")
	public ResponseEntity<?> getTravelListBySidoCode(int sidocode) throws Exception {
		return new ResponseEntity<>(attractionInfoService.getTravelListBySido(sidocode), HttpStatus.OK);
	}
	
	@ApiOperation(value = "시도, 구군 코드로 여행지 검색", notes="시도, 구군 코드에 해당하는 여행지 정보를 반환합니다.")
	@GetMapping("/info/region2")
	public ResponseEntity<?> getTravelListBySidoGugunCode(int sidocode, int guguncode) throws Exception {
		return new ResponseEntity<>(attractionInfoService.getTravelListBySidoGugun(sidocode, guguncode), HttpStatus.OK);
	}
	
	@ApiOperation(value ="시도 코드 목록", notes="시도 코드 목록을 반환합니다.")
	@GetMapping("/info/sido")
	public ResponseEntity<?> getSidoList() throws Exception {


		FileInputStream fileStream = new FileInputStream(Paths.get("").toAbsolutePath().toString() + "/src/main/resources/sido.ser");
		ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);
		
		Object obj = objectInputStream.readObject();
		objectInputStream.close();
		
		HashMap<String, Integer> hmap = (HashMap)obj;
		
		return new ResponseEntity<>(hmap, HttpStatus.OK); 
	}
	
	@ApiOperation(value ="구군 코드 목록", notes="구군 코드 목록을 반환합니다.")
	@GetMapping("/info/gugun")
	public ResponseEntity<?> getGugunList(Integer sidocode) throws Exception {
		FileInputStream fileStream = new FileInputStream(Paths.get("").toAbsolutePath().toString() + "/src/main/resources/gugun.ser");
		ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);
		
		Object obj = objectInputStream.readObject();
		objectInputStream.close();
		
		HashMap<Integer, Object> hmap = (HashMap)obj;
		System.out.println(hmap.get(1));
		
		return new ResponseEntity<>(hmap.get(sidocode), HttpStatus.OK); 
	}
	
	@ApiOperation(value = "컨텐츠 아이디 검색", notes="조건에 해당하는 컨텐츠 아이디를 반환합니다.")
	@PostMapping("/info/contentid")
	public ResponseEntity<?> getContentId(@RequestBody AttractionInfo attractionInfo) throws Exception {
		System.out.println(attractionInfo.toString());
		return new ResponseEntity<>(attractionInfoService.getTravelSpot(attractionInfo), HttpStatus.OK);
	}
	
	@ApiOperation(value = "컨텐츠 아이디로 여행지 정보 가져오기", notes="컨텐츠 아이디와 일치하는 여행지 정보를 반환합니다.")
	@PostMapping("/info/contentid/detail")
	public ResponseEntity<?> getAttractionInfoByContentId(@RequestParam(value="contentId") Integer contentId) throws Exception {
		return new ResponseEntity<>(attractionInfoService.getTravelInfoByContentId(contentId), HttpStatus.OK);
	}


	@ApiOperation(value = "컨텐츠 아이디로 여행지 정보 가져오기", notes="컨텐츠 아이디와 일치하는 여행지 정보를 반환합니다.")
	@GetMapping("/information/{content_id}")
	public ResponseEntity<?> getAttractionInformationByContentId(@PathVariable(value="content_id") Integer contentId) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("info", attractionInfoService.getTravelInfoByContentId(contentId));
		result.put("description", attractionDescriptionService.getAttractionDescription(contentId));
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
