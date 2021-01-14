package com.cos.testWeb.service;

import java.util.List;

import com.cos.testWeb.user.User;
import com.cos.testWeb.user.UserDao;
import com.cos.testWeb.user.dto.JoinReqDto;
import com.cos.testWeb.user.dto.LoginReqDto;

public class UserService {

	private UserDao userDao;
	
	public UserService() {
		userDao = new UserDao();
	}
	
	public int 회원가입(JoinReqDto dto) {
		int result = userDao.save(dto);
		
		return result;
	}
	
	public User 로그인(LoginReqDto dto) {
		return userDao.login(dto);
	}
	
	public int 아이디중복체크(String username) {
		int result = userDao.findByUsername(username);
		
		return result;
	}
	
	public List<User> 회원목록보기() {
		return userDao.findAll();
	}
	
	public int 회원삭제(int id) {
		return userDao.delete(id);
	}
}
