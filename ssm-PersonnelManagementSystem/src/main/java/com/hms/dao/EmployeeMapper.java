package com.hms.dao;

import com.hms.bean.EmpPart;
import com.hms.bean.Employee;
import com.hms.bean.EmployeeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmployeeMapper {
    long countByExample(EmployeeExample example);

    int deleteByExample(EmployeeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    int insertSelective(Employee record);

    List<Employee> selectByExample(EmployeeExample example);

    Employee selectByPrimaryKey(Integer id);
    
    List<EmpPart> selectByExamplePartWithDeptAndJob(EmployeeExample example);

    EmpPart selectByPrimaryKeyPartWithDeptAndJob(Integer id);

    int updateByExampleSelective(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByExample(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

	List<EmpPart> fuzzyQuery(@Param("keyword") String keyword);
}