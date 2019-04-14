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

import com.hms.bean.Department;
import com.hms.bean.Overtime;
import com.hms.bean.Msg;
import com.hms.service.OvertimeService;

@Controller
public class OvertimeController {
	@Autowired
	private OvertimeService overtimeService;

	@ResponseBody
	@RequestMapping("/overtimes/select")
	public Msg getOvertimes() {
		List<Overtime> overtimes = overtimeService.getAll();
		return Msg.success().add("overtimes", overtimes);
	}
	
	@ResponseBody
	@RequestMapping("/overtimesofmonth/select")
	public Msg getOvertimesOfMonth(@RequestParam(value = "year") String year,@RequestParam(value = "month") String month) {
		List<Overtime> overtimes = overtimeService.getOvertimesOfMonth(year,month);
		return Msg.success().add("overtimes", overtimes);
	}
	
	@ResponseBody
	@RequestMapping("/uncheckovertimes/select")
	public Msg getuncheckOvertimes() {
		List<Overtime> overtimes = overtimeService.getUncheck();
		return Msg.success().add("overtimes", overtimes);
	}
	
	@ResponseBody
	@RequestMapping("/overtime/update")
	public Msg updateOvertime(@RequestBody Overtime overtime) {
		overtimeService.updateOvertime(overtime);
		return Msg.success();
	}
	
	@ResponseBody
	@RequestMapping("/overtimes/delete")
	public Msg deleteOvertimes(@RequestParam(value = "idList[]") List<Integer> idList) {
		overtimeService.deleteOvertimes(idList);
		return Msg.success();
	}
}
