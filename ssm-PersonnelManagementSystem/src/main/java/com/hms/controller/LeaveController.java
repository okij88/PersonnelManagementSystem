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
import com.hms.bean.Leave;
import com.hms.bean.Msg;
import com.hms.service.LeaveService;

@Controller
public class LeaveController {
	@Autowired
	private LeaveService leaveService;

	@ResponseBody
	@RequestMapping("/leaves/select")
	public Msg getLeaves() {
		List<Leave> leaves = leaveService.getAll();
		return Msg.success().add("leaves", leaves);
	}
	
	@ResponseBody
	@RequestMapping("/leavesofmonth/select")
	public Msg getLeavesOfMonth(@RequestParam(value = "year") String year,@RequestParam(value = "month") String month) {
		List<Leave> leaves = leaveService.getLeavesOfMonth(year,month);
		return Msg.success().add("leaves", leaves);
	}
	
	@ResponseBody
	@RequestMapping("/uncheckleaves/select")
	public Msg getuncheckLeaves() {
		List<Leave> leaves = leaveService.getUncheck();
		return Msg.success().add("leaves", leaves);
	}
	
	@ResponseBody
	@RequestMapping("/leave/update")
	public Msg updateLeave(@RequestBody Leave leave) {
		leaveService.updateLeave(leave);
		return Msg.success();
	}
	
	@ResponseBody
	@RequestMapping("/leaves/delete")
	public Msg deleteLeaves(@RequestParam(value = "idList[]") List<Integer> idList) {
		leaveService.deleteLeaves(idList);
		return Msg.success();
	}
}
