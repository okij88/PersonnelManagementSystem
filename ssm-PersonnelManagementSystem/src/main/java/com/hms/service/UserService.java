package com.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.bean.User;
import com.hms.bean.UserExample;
import com.hms.bean.UserExample.Criteria;
import com.hms.dao.UserMapper;
@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	public List<User> getAll() {
		// TODO Auto-generated method stub
		return userMapper.selectByExampleWithEmp(null);
	}

	public boolean checkloginname(String loginname) {
		// TODO Auto-generated method stub
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andLoginnameEqualTo(loginname);
		long count = userMapper.countByExample(example);
		return count == 0;
	}

	public void saveUser(User user) {
		// TODO Auto-generated method stub
		userMapper.insertSelective(user);
	}

	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub
		userMapper.deleteByPrimaryKey(id);
	}

	public void deleteUsers(List<Integer> idList) {
		// TODO Auto-generated method stub
		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andIdIn(idList);
		userMapper.deleteByExample(userExample);
	}

	public User getUser(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	public void updateUser(User user) {
		// TODO Auto-generated method stub
		userMapper.updateByPrimaryKeySelective(user);
	}

	public List<User> searchUsers(String keyword) {
		// TODO Auto-generated method stub
		return userMapper.fuzzyQuery(keyword);
	}

	public void autoAddUser() {
		// TODO Auto-generated method stub
		System.out.println();
		int start = (int) (userMapper.countByExample(null) +1);
		System.out.println(start);
		for(int i=start;i<start+10;i++) {
			User user = new User();
			user.setLoginname("test" + i);
			user.setPassword("test" + i);
			user.setUsername("²âÊÔÓÃ»§");
			user.setUserstatus(1);
			userMapper.insertSelective(user);
		}
	}

}
