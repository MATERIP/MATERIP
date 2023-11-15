package com.ssafy.materip.model.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.materip.model.dto.User;

@Mapper
public interface UserDao {
	int createUser(User user) throws SQLException;
	
	int idCheck(String userId) throws SQLException;
	
	User readUserById(String userId) throws SQLException;
	
	int updateUser(User user) throws SQLException;
	
	int deleteUser(String userId) throws SQLException;
	
	List<User> readAllUsers() throws SQLException;
	
	User readUser(User user) throws SQLException;
}
