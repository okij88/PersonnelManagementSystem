package com.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.bean.Department;
import com.hms.bean.DepartmentExample;
import com.hms.bean.DepartmentExample.Criteria;
import com.hms.dao.DepartmentMapper;

@Service
public class DepartmentService {
	@Autowired
	private DepartmentMapper departmentMapper;

	public List<Department> getAll() {
		// TODO Auto-generated method stub
		return departmentMapper.selectByExample(null);
	}

	public void saveDept(Department department) {
		// TODO Auto-generated method stub
		departmentMapper.insertSelective(department);
	}

	public boolean checkname(String name) {
		// TODO Auto-generated method stub
		DepartmentExample example = new DepartmentExample();
		Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(name);
		long count =departmentMapper.countByExample(example);
		return count == 0;
	}

	public void deleteDept(Integer id) {
		// TODO Auto-generated method stub
		departmentMapper.deleteByPrimaryKey(id);
	}

	public void deleteDepts(List<Integer> idList) {
		// TODO Auto-generated method stub
		DepartmentExample example = new DepartmentExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(idList);
		departmentMapper.deleteByExample(example);
	}

	public Department getDept(Integer id) {
		// TODO Auto-generated method stub
		return departmentMapper.selectByPrimaryKey(id);
	}

	public void updateDept(Department department) {
		// TODO Auto-generated method stub
		departmentMapper.updateByPrimaryKeySelective(department);
	}

}
