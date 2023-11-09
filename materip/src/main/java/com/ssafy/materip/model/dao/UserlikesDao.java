package com.ssafy.materip.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserlikesDao {
	// 새로운 '좋아요' 기록 생성
	int createUserlikes(String userId, String likedBy);
	
	// 사용자의 모든 '좋아요' 읽기
	List<String> readUserlikes(String userId);
	
	// 사용자의 '좋아요' 수 읽기
	int readUserlikesCount(String userId);
	
	// '좋아요' 기록 삭제	
	int deleteUserlikes(String userId, String likedBy);
}
