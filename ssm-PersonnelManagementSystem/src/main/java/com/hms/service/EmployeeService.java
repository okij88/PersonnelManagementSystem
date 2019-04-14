package com.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.bean.EmpPart;
import com.hms.bean.Employee;
import com.hms.bean.EmployeeExample;
import com.hms.bean.EmployeeExample.Criteria;
import com.hms.dao.EmployeeMapper;
@Service
public class EmployeeService {
	@Autowired
	private EmployeeMapper employeeMapper;

	public EmpPart getEmp(Integer id) {
		// TODO Auto-generated method stub
		return employeeMapper.selectByPrimaryKeyPartWithDeptAndJob(id);
	}

	public List<EmpPart> getAll() {
		// TODO Auto-generated method stub
		return employeeMapper.selectByExamplePartWithDeptAndJob(null);
	}

	public void saveEmp(Employee employee) {
		// TODO Auto-generated method stub
		employeeMapper.insertSelective(employee);
	}

	public void deleteEmp(Integer id) {
		// TODO Auto-generated method stub
		employeeMapper.deleteByPrimaryKey(id);
	}

	public void deleteEmps(List<Integer> idList) {
		// TODO Auto-generated method stub
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(idList);
		employeeMapper.deleteByExample(example);
	}

	public void updateEmp(Employee employee) {
		// TODO Auto-generated method stub
		employeeMapper.updateByPrimaryKeySelective(employee);
	}


	public Employee getMessage(Integer id) {
		// TODO Auto-generated method stub
		return employeeMapper.selectByPrimaryKey(id);
	}

	public List<EmpPart> searchEmps(String keyword) {
		// TODO Auto-generated method stub
		return employeeMapper.fuzzyQuery(keyword);
	}

}
