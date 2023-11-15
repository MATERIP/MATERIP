package com.ssafy.materip.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.materip.model.dto.AuthDto;

@Mapper
public interface AuthDao {
	AuthDto select(String userId);
	int insert(AuthDto authDto);
	int update(AuthDto authDto);
}