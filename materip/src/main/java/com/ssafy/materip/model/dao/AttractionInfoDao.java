package com.ssafy.materip.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.materip.model.dto.AttractionInfo;

@Mapper
public interface AttractionInfoDao {
	List<AttractionInfo> getAttractionInfoList();
	List<String> getAttractionNameList();
	List<String> getAttractionSearchNameList(String title);
	
	
	List<AttractionInfo> getAttractionInfoBySido(int sidoCode);
	List<String> getAttractionInfoBySidoGugun(int sidoCode, int gugunCode);
	Integer getAttractionContentId(AttractionInfo attractionInfo);
}
