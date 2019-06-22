package com.hms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hms.bean.EmpPart;
import com.hms.bean.Employee;
import com.hms.bean.Msg;
import com.hms.bean.User;
import com.hms.service.EmployeeService;

@Controller
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@RequestMapping("/emp/select")
	@ResponseBody
	public Msg getemp(@RequestParam(value = "id") Integer id) {
		EmpPart employee = employeeService.getEmp(id);
		return Msg.success().add("emp", employee);
	}

	@ResponseBody
	@RequestMapping("/emps/select")
	public Msg getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		PageHelper.startPage(pn, 15);
		List<EmpPart> emps = employeeService.getAll();
		for(EmpPart emp : emps) {
			System.out.println(emp.getName());
		}
		PageInfo page = new PageInfo(emps, 5);
		return Msg.success().add("pageInfo", page);
	}

	@RequestMapping("/emp/add")
	@ResponseBody
	public Msg saveEmp(@RequestBody Employee employee) {
		employeeService.saveEmp(employee);
		return Msg.success();
	}

	@RequestMapping("/emp/delete")
	@ResponseBody
	public Msg deleteEmp(@RequestParam(value = "id") Integer id) {
		employeeService.deleteEmp(id);
		return Msg.success();
	}

	@RequestMapping("emps/delete")
	@ResponseBody
	public Msg deleteEmps(@RequestParam(value = "idList[]") List<Integer> idList) {
		System.out.println(idList);
		employeeService.deleteEmps(idList);
		return Msg.success();
	}
	
	@RequestMapping("/emp/update")
	@ResponseBody
	public Msg updateEmp(@RequestBody Employee employee) {
		employeeService.updateEmp(employee);
		return Msg.success();
	}
	@RequestMapping("/emp/show")
	@ResponseBody
	public Msg getMessage(@RequestParam(value = "id") Integer id) {
		Employee employee = employeeService.getMessage(id);
		return Msg.success().add("message", employee);
	}
	@RequestMapping("emps/search")
	@ResponseBody
	public Msg searchUsers(@RequestParam(value = "e") String e) {
		System.out.println(e);
		List<EmpPart> emps = employeeService.searchEmps(e);
		return Msg.success().add("emps", emps);
	}
}
