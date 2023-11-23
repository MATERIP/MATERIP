package com.ssafy.materip.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.materip.model.dto.AttractionDetail;
@Mapper
public interface AttractionDetailDao {
	
	AttractionDetail getAttractionDetail(int contentId);
}
