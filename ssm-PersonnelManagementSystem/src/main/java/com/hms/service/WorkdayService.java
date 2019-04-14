package com.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.bean.Workday;
import com.hms.dao.WorkdayMapper;

@Service
public class WorkdayService {
	@Autowired
	private WorkdayMapper workdayMapper;

	public List<Workday> getAll() {
		return workdayMapper.selectByExample(null);
	}

	public void saveWorkdays(List<Workday> workdays) {
		// TODO Auto-generated method stub
		workdayMapper.insertBatch(workdays);
	}

	public void truncateTable() {
		// TODO Auto-generated method stub
		workdayMapper.truncateTable();
	}

}
