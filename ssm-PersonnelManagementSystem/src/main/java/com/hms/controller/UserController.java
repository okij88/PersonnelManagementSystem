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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hms.bean.Msg;
import com.hms.bean.User;
import com.hms.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping("/user/select")
	@ResponseBody
	public Msg getUser(@RequestParam(value = "id") Integer id) {
		User user = userService.getUser(id);
		return Msg.success().add("user", user);
	}

	@ResponseBody
	@RequestMapping("/users/select")
	public Msg getUsers(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		System.out.println(pn);
		PageHelper.startPage(pn, 15);
		List<User> users = userService.getAll();
		PageInfo page = new PageInfo(users, 5);
		return Msg.success().add("pageInfo", page);
	}

	@RequestMapping("/user/update")
	@ResponseBody
	public Msg updateUser(@RequestBody User user) {
		userService.updateUser(user);
		return Msg.success();
	}

	@ResponseBody
	@RequestMapping("/user/check")
	public Msg getUserCheck(@RequestParam(value = "loginname") String loginname) {
		boolean checkResult = userService.checkloginname(loginname);
		if (checkResult) {
			return Msg.success();
		} else {
			return Msg.fail();
		}
	}

	@RequestMapping("/user/add")
	@ResponseBody
	public Msg saveUser(@RequestBody @Valid User user, BindingResult result) {
		System.out.println(user.toString());
		if (result.hasErrors()) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				System.out.println("´íÎó×Ö¶ÎÃû£º" + fieldError.getField());
				System.out.println("´íÎóÐÅÏ¢£º" + fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);
		} else {
			userService.saveUser(user);
			return Msg.success();
		}
	}

	@RequestMapping("/user/autoadd")
	@ResponseBody
	public Msg autoAddUser() {
		userService.autoAddUser();
		return Msg.success();
	}

	@RequestMapping("/user/delete")
	@ResponseBody
	public Msg deleteUser(@RequestParam(value = "id") Integer id) {
		userService.deleteUser(id);
		return Msg.success();
	}

	@RequestMapping("users/delete")
	@ResponseBody
	public Msg deleteUsers(@RequestParam(value = "idList[]") List<Integer> idList) {
		System.out.println(idList);
		userService.deleteUsers(idList);
		return Msg.success();
	}

	@RequestMapping("users/search")
	@ResponseBody
	public Msg searchUsers(@RequestParam(value = "e") String e) {
		System.out.println(e);
		List<User> users = userService.searchUsers(e);
		return Msg.success().add("users", users);
	}
}
