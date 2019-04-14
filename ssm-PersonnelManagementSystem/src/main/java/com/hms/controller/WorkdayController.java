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

import com.hms.bean.Workday;
import com.hms.bean.Msg;
import com.hms.service.WorkdayService;

@Controller
public class WorkdayController {
	@Autowired
	private WorkdayService workdayService;

	@ResponseBody
	@RequestMapping("/workdays/select")
	public Msg getWorkdays() {
		List<Workday> workdays = workdayService.getAll();
		return Msg.success().add("workdays", workdays);
	}

	@RequestMapping("/workdays/add")
	@ResponseBody
	public Msg saveWorkdays(@RequestBody List<Workday> workdays) {
		workdayService.truncateTable();
		workdayService.saveWorkdays(workdays);
		return Msg.success();
	}

}
