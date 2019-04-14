package com.hms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hms.bean.Department;
import com.hms.bean.Msg;
import com.hms.bean.User;
import com.hms.service.DepartmentService;

@Controller
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;

	@RequestMapping("/dept/select")
	@ResponseBody
	public Msg getDept(@RequestParam(value = "id") Integer id) {
		Department department = departmentService.getDept(id);
		return Msg.success().add("dept", department);
	}

	@ResponseBody
	@RequestMapping("/depts/select")
	public Msg getDepts() {
		List<Department> depts = departmentService.getAll();
		return Msg.success().add("depts", depts);
	}

	@RequestMapping("/dept/add")
	@ResponseBody
	public Msg saveDept(@RequestBody Department department) {
		departmentService.saveDept(department);
		return Msg.success();
	}

	@ResponseBody
	@RequestMapping("/dept/check")
	public Msg getDeptCheck(@RequestParam(value = "name") String name) {
		boolean checkResult = departmentService.checkname(name);
		if (checkResult) {
			return Msg.success();
		} else {
			return Msg.fail();
		}
	}

	@RequestMapping("/dept/delete")
	@ResponseBody
	public Msg deleteDept(@RequestParam(value = "id") Integer id) {
		departmentService.deleteDept(id);
		return Msg.success();
	}

	@RequestMapping("depts/delete")
	@ResponseBody
	public Msg deleteDepts(@RequestParam(value = "idList[]") List<Integer> idList) {
		System.out.println(idList);
		departmentService.deleteDepts(idList);
		return Msg.success();
	}
	
	@RequestMapping("/dept/update")
	@ResponseBody
	public Msg updateDept(@RequestBody Department department) {
		departmentService.updateDept(department);
		return Msg.success();
	}
}
