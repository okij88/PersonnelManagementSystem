package com.hms.bean;

import java.util.Date;

import javax.validation.constraints.Pattern;

public class UserPart {
	private Integer id;

	private String loginname;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname == null ? null : loginname.trim();
	}
}