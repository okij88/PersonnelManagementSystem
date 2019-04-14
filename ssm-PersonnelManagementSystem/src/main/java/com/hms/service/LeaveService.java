package com.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.bean.Leave;
import com.hms.bean.LeaveExample;
import com.hms.bean.LeaveExample.Criteria;
import com.hms.dao.LeaveMapper;

@Service
public class LeaveService {
	@Autowired
	private LeaveMapper leaveMapper;

	public List<Leave> getAll() {
		return leaveMapper.selectByExampleWithUserAndEmp(null);
	}

	public List<Leave> getUncheck() {
		LeaveExample example = new LeaveExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(0);
		return leaveMapper.selectByExampleWithUserAndEmp(example);
	}

	public void updateLeave(Leave leave) {
		leaveMapper.updateByPrimaryKeySelective(leave);
	}

	public void deleteLeaves(List<Integer> idList) {
		// TODO Auto-generated method stub
		LeaveExample example = new LeaveExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(idList);
		leaveMapper.deleteByExample(example);
	}

	public List<Leave> getLeavesOfMonth(String year, String month) {
		LeaveExample example = new LeaveExample();
		Criteria criteria = example.createCriteria();
		criteria.andYearEqualTo(year).andMonthEqualTo(month);
		return leaveMapper.selectByExampleWithUserAndEmp(example);
	}

}
