package com.ssafy.materip.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.materip.model.dto.AttractionDescription;

@Mapper
public interface AttractionDescriptionDao {
	
	AttractionDescription getAttractionDescription(int contentId);
}
