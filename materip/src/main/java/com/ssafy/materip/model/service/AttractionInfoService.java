package com.ssafy.materip.model.service;

import java.util.List;

import com.ssafy.materip.model.dto.AttractionInfo;

public interface AttractionInfoService {
    // 여행지 추가
	List<String> getTravelNameList();
	List<AttractionInfo> getTravelList();
	List<AttractionInfo> getTravelNameSearchList(String title);
	AttractionInfo getAttractionInfo(int contentId);
}
