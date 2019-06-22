package com.hms.bean;

import java.util.Date;

import javax.validation.constraints.Pattern;

public class User {
	private Integer id;

	@Pattern(regexp = "(^([a-zA-Z0-9_-]{5,16}$))", message = "登录名为5-16位英文和数字的组合")
	private String loginname;
	@Pattern(regexp = "(^([a-zA-Z0-9_-]{5,16}$))", message = "登录名为5-16位英文和数字的组合")
	private String password;

	private Integer userstatus;

	private Date createdate;

	private String username;

	private Integer empId;

	private Employee employee;

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", loginname=" + loginname + ", password=" + password + ", userstatus=" + userstatus
				+ ", createdate=" + createdate + ", username=" + username + ", empId=" + empId + ", employee="
				+ employee + "]";
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public Integer getUserstatus() {
		return userstatus;
	}

	public void setUserstatus(Integer userstatus) {
		this.userstatus = userstatus;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
}