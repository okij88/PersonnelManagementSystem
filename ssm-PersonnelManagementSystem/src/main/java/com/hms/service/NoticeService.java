package com.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.bean.Notice;
import com.hms.bean.NoticeExample;
import com.hms.dao.NoticeMapper;
@Service
public class NoticeService {
	@Autowired
	private NoticeMapper noticeMapper;

	public List<Notice> getAll() {
		// TODO Auto-generated method stub
		return noticeMapper.selectByExampleWithUserAndEmp(null);
	}

	public void saveNotice(Notice notice) {
		// TODO Auto-generated method stub
		noticeMapper.insertSelective(notice);
	}

	public void deleteNotice(Integer id) {
		// TODO Auto-generated method stub
		noticeMapper.deleteByPrimaryKey(id);
	}

	public void deleteNotices(List<Integer> idList) {
		// TODO Auto-generated method stub
		NoticeExample example = new NoticeExample();
		com.hms.bean.NoticeExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(idList);
		noticeMapper.deleteByExample(example);
	}

	public Notice getNotice(Integer id) {
		// TODO Auto-generated method stub
		return noticeMapper.selectByPrimaryKey(id);
	}

	public void updateNotice(Notice notice) {
		// TODO Auto-generated method stub
		noticeMapper.updateByPrimaryKeySelective(notice);
	}
}
