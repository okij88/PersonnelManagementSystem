package com.hms.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hms.bean.Msg;
import com.hms.bean.User;
import com.hms.service.LoginService;
import com.hms.utils.TokenUtil;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;

	@RequestMapping("/login")
	@ResponseBody
	public Msg login(@RequestBody Map user) {
		Map<String, Object> map = new HashMap<String, Object>();
		String loginname = user.get("loginname").toString();
		String password = user.get("password").toString();
		User user2 = loginService.checkLogin(loginname,password);
		if(user2!=null) {
			String username = user2.getUsername();
			String token = TokenUtil.sign(loginname,password,username);
	        if (token != null){
	            map.put("code", "100");
	            map.put("message","认证成功");
	            map.put("token", token);
	            return Msg.success().add("result", map);
	        }
		}
		map.put("code", "200");
	    map.put("message","认证失败");
	    return Msg.fail().add("result", map);
	}
}
