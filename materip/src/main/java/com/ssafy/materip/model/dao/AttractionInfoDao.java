package com.ssafy.materip.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.materip.model.dto.AttractionInfo;

@Mapper
public interface AttractionInfoDao {
	List<AttractionInfo> getAttractionInfoList();
	List<String> getAttractionNameList();
	List<AttractionInfo> getAttractionSearchNameList(String title);
	AttractionInfo getAttractionInfo(int contentId);
}
