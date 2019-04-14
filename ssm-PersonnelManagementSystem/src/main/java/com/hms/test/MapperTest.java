package com.hms.test;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hms.bean.Employee;
import com.hms.bean.User;
import com.hms.dao.DepartmentMapper;
import com.hms.dao.EmployeeMapper;
import com.hms.dao.UserMapper;

//≤‚ ‘DAO
@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MapperTest {
	@Autowired
	DepartmentMapper departmentMapper;
	@Autowired
	EmployeeMapper employeeMapper;
	@Autowired
	SqlSession sqlSession;
	@Test
	public void testCRUD() {
		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
		for(int i=4;i<100;i++) {
			Employee emp = new Employee();
			emp.setId(null);
			emp.setDeptId(1);
			emp.setJobId(2);
			emp.setName("Jack" + i);
			emp.setSex(1);
			emp.setCardId("34556225"+i);
			emp.setAddress("∫º÷›");
			emp.setPhone("123456"+i);
			emp.setEmail("jack"+i+"@test.com");
			mapper.insertSelective(emp);
		}
	}
}
