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

import com.hms.bean.Job;
import com.hms.bean.Msg;
import com.hms.service.JobService;

@Controller
public class JobController {
	@Autowired
	private JobService jobService;

	@RequestMapping("/job/select")
	@ResponseBody
	public Msg getJob(@RequestParam(value = "id") Integer id) {
		Job job = jobService.getJob(id);
		return Msg.success().add("job", job);
	}

	@ResponseBody
	@RequestMapping("/jobs/select")
	public Msg getJobs() {
		List<Job> jobs = jobService.getAll();
		return Msg.success().add("jobs", jobs);
	}

	@RequestMapping("/job/add")
	@ResponseBody
	public Msg saveJob(@RequestBody Job job) {
		jobService.saveJob(job);
		return Msg.success();
	}

	@ResponseBody
	@RequestMapping("/job/check")
	public Msg getJobCheck(@RequestParam(value = "name") String name) {
		boolean checkResult = jobService.checkname(name);
		if (checkResult) {
			return Msg.success();
		} else {
			return Msg.fail();
		}
	}

	@RequestMapping("/job/delete")
	@ResponseBody
	public Msg deleteJob(@RequestParam(value = "id") Integer id) {
		jobService.deleteJob(id);
		return Msg.success();
	}

	@RequestMapping("/jobs/delete")
	@ResponseBody
	public Msg deleteJobs(@RequestParam(value = "idList[]") List<Integer> idList) {
		System.out.println(idList);
		jobService.deleteJobs(idList);
		return Msg.success();
	}
	
	@RequestMapping("/job/update")
	@ResponseBody
	public Msg updateJob(@RequestBody Job job) {
		jobService.updateJob(job);
		return Msg.success();
	}
}
