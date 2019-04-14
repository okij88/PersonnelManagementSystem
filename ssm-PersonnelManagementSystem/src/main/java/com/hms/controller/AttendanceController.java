package com.hms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hms.bean.Attendance;
import com.hms.bean.Msg;
import com.hms.service.AttendanceService;

@Controller
public class AttendanceController {
	@Autowired
	private AttendanceService attendanceService;

	@RequestMapping("/attendance/select")
	@ResponseBody
	public Msg getAttendance(@RequestParam(value = "id") Integer id) {
		Attendance attendance = attendanceService.getAttendance(id);
		return Msg.success().add("attendance", attendance);
	}

	@ResponseBody
	@RequestMapping("/attendances/select")
	public Msg getAttendances() {
		List<Attendance> attendances = attendanceService.getAll();
		return Msg.success().add("attendances", attendances);
	}

	@RequestMapping("/attendances/add")
	@ResponseBody
	public Msg saveAttendance(@RequestBody List<Attendance> attendances) {
		for(int i=0;i<attendances.size();i++) {
			attendanceService.saveAttendance(attendances.get(i));
		}
		return Msg.success();
	}

	@RequestMapping("/attendances/update")
	@ResponseBody
	public Msg updateattendance(@RequestBody List<Attendance> attendances) {
		attendanceService.clear();
		for(int i=0;i<attendances.size();i++) {
			attendanceService.saveAttendance(attendances.get(i));
		}
		return Msg.success();
	}
}
