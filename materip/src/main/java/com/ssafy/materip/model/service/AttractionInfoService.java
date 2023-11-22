package com.ssafy.materip.model.service;

import java.util.List;

import com.ssafy.materip.model.dto.AttractionInfo;

public interface AttractionInfoService {
    // 여행지 추가
	List<String> getTravelNameList();
	List<AttractionInfo> getTravelList();
	List<String> getTravelNameSearchList(String title);
	
	List<AttractionInfo> getTravelListBySido(int sidoCode);
	List<String> getTravelListBySidoGugun(int sidoCode, int gugunCode);
}
