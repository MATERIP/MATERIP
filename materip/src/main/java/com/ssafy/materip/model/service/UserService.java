package com.ssafy.materip.model.service;

import java.util.List;

import com.ssafy.materip.model.dto.User;

public interface UserService {

	int joinMember(User user) throws Exception;
	
	User getUser(String userId) throws Exception;
	
	int modifyUserInfo(User user) throws Exception;
	
	int withdrawal(String userId) throws Exception;
	
	List<User> getUserList() throws Exception;
}
