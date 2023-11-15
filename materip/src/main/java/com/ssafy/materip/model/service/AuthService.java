package com.ssafy.materip.model.service;

import com.ssafy.materip.model.dto.AuthDto;

public interface AuthService {
	int setRefreshToken(AuthDto authDto);
	AuthDto getRefreshToken(String userId);
	int updateRefreshToken(AuthDto authDto);
	
}