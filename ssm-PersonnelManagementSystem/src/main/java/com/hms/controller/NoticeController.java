package com.hms.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hms.bean.Msg;
import com.hms.bean.Notice;
import com.hms.service.NoticeService;

@Controller
public class NoticeController {
	@Autowired
	private NoticeService noticeService;

	@RequestMapping("/notice/select")
	@ResponseBody
	public Msg getNotice(@RequestParam(value = "id") Integer id) {
		Notice notice = noticeService.getNotice(id);
		return Msg.success().add("notice", notice);
	}

	@ResponseBody
	@RequestMapping("/notices/select")
	public Msg getNotices() {
		List<Notice> notices = noticeService.getAll();
		return Msg.success().add("notices", notices);
	}

	@RequestMapping("/notice/add")
	@ResponseBody
	public Msg saveNotice(@RequestBody Notice notice) {
		noticeService.saveNotice(notice);
		return Msg.success();
	}

	@RequestMapping("/notice/delete")
	@ResponseBody
	public Msg deleteNotice(@RequestParam(value = "id") Integer id) {
		noticeService.deleteNotice(id);
		return Msg.success();
	}

	@RequestMapping("/notices/delete")
	@ResponseBody
	public Msg deleteNotices(@RequestParam(value = "idList[]") List<Integer> idList) {
		System.out.println(idList);
		noticeService.deleteNotices(idList);
		return Msg.success();
	}
	
	@RequestMapping("/notice/update")
	@ResponseBody
	public Msg updateNotice(@RequestBody Notice notice) {
		noticeService.updateNotice(notice);
		return Msg.success();
	}
}
