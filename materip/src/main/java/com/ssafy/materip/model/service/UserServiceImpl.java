package com.ssafy.materip.model.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.materip.model.dao.UserDao;
import com.ssafy.materip.model.dao.UserlikesDao;
import com.ssafy.materip.model.dto.User;
import com.ssafy.materip.model.dto.Userlikes;

@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	private final UserlikesDao userlikesDao;

	public UserServiceImpl(UserDao userDao, UserlikesDao userlikesDao) {
		this.userDao = userDao;
		this.userlikesDao = userlikesDao;
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
	public User login(User user) throws Exception {
		return userDao.readUser(user);
	}

	@Override
	public User getUser(String userId) throws Exception {
		return userDao.readUserById(userId);
	}

	@Override
	public int modifyUserInfo(User user) throws Exception {
		user.setModifiedAt(Timestamp.valueOf(LocalDateTime.now()));
		return userDao.updateUser(user);
	}

	@Override
	public int withdrawal(String userId) throws Exception {
		return userDao.deleteUser(userId);
	}

	@Override
	public int likeUser(String likedBy, String userId) throws Exception {
		if (likedBy.equals(userId)) {
			throw new IllegalArgumentException("자기 자신은 좋아요를 누를 수 없습니다!");
		}

		Userlikes userlikes = new Userlikes();
		userlikes.setId(userId);
		userlikes.setLikedBy(likedBy);
		return userlikesDao.createUserlikes(userId, likedBy);
	}

	@Override
	public int unlikeUser(String likedBy, String userId) {
		return userlikesDao.deleteUserlikes(userId, likedBy);
	}

	@Override
	public int readUserlikesCount(String userId) throws Exception {
		return userlikesDao.readUserlikesCount(userId);
	}

	@Override
	public List<String> readUserlikes(String userId) throws Exception {
		return userlikesDao.readUserlikes(userId);
	}

	@Override
	public int getRankUserlikes(String userId) throws Exception {
		return userlikesDao.getRankUserlikes(userId);
	}

}
