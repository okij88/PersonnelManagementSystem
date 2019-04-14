package com.hms.bean;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Value;

public class Overtime {
	private Integer id;

	private Integer userId;

	private Integer empId;

	private String year;

	private String month;

	private String day;

	private Date subtime;

	private String reason;

	private Integer status;

	private Map<String, Value> emp;

	private Map<String, Value> user;

	public Map<String, Value> getEmp() {
		return emp;
	}

	public void setEmp(Map<String, Value> emp) {
		this.emp = emp;
	}

	public Map<String, Value> getUser() {
		return user;
	}

	public void setUser(Map<String, Value> user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year == null ? null : year.trim();
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month == null ? null : month.trim();
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day == null ? null : day.trim();
	}

	public Date getSubtime() {
		return subtime;
	}

	public void setSubtime(Date subtime) {
		this.subtime = subtime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason == null ? null : reason.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}