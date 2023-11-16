package com.ssafy.materip.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.materip.model.dao.AuthDao;
import com.ssafy.materip.model.dto.AuthDto;

@Service
public class AuthServiceImpl implements AuthService {

	private final AuthDao authDao;
	
	private AuthServiceImpl(AuthDao jwtDao) {
		this.authDao = jwtDao;
	}
	
	@Override
	public int setRefreshToken(AuthDto authDto) {
		return authDao.insert(authDto);
	}
	
	@Override
	public AuthDto getRefreshToken(String userId) {
		return authDao.select(userId);
	}
	
	@Override
	public int updateRefreshToken(AuthDto authDto) {
		System.out.println("here!!");
		return authDao.update(authDto);
	}
	
}