package com.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.bean.User;
import com.hms.bean.UserExample;
import com.hms.bean.UserExample.Criteria;
import com.hms.dao.UserMapper;

@Service
public class LoginService {
	@Autowired
	private UserMapper userMapper;

	public User checkLogin(String loginname, String password) {
		// TODO Auto-generated method stub
		UserExample example = new UserExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andLoginnameEqualTo(loginname);
		createCriteria.andPasswordEqualTo(password);
		List<User> userList = userMapper.selectByExample(example);
		if(userList.get(0)!=null) {
			return userList.get(0);
		}
		return null;
	}
	
	
	
}
