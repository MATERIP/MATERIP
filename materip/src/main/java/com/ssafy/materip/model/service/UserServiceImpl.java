package com.ssafy.materip.model.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.materip.model.dao.UserDao;
import com.ssafy.materip.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public List<User> getUserList() throws Exception {
		return userDao.readAllUsers();
	}

	@Override
	public int idCheck(String userId) throws Exception {
		return userDao.idCheck(userId);
	}

	@Override
	public int joinMember(User user) throws Exception {
		user.setJoinDate(Timestamp.valueOf(LocalDateTime.now()));
		user.setModifiedAt(Timestamp.valueOf(LocalDateTime.now()));
		return userDao.createUser(user);
	}

	@Override
	public User getUser(String userId) throws Exception {
		return userDao.readUserById(userId);
	}

	@Override
	public int modifyUserInfo(User user) throws Exception {
		return userDao.updateUser(user);
	}

	@Override
	public int withdrawal(String userId) throws Exception {
		return userDao.deleteUser(userId);
	}

}
