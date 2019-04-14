package com.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.bean.Overtime;
import com.hms.bean.OvertimeExample;
import com.hms.bean.OvertimeExample.Criteria;
import com.hms.dao.OvertimeMapper;
@Service
public class OvertimeService {
	@Autowired
	private OvertimeMapper overtimeMapper;

	public List<Overtime> getAll() {
		return overtimeMapper.selectByExampleWithUserAndEmp(null);
	}

	public List<Overtime> getUncheck() {
		OvertimeExample example = new OvertimeExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(0);
		return overtimeMapper.selectByExampleWithUserAndEmp(example);
	}

	public void updateOvertime(Overtime overtime) {
		overtimeMapper.updateByPrimaryKeySelective(overtime);
	}

	public void deleteOvertimes(List<Integer> idList) {
		// TODO Auto-generated method stub
		OvertimeExample example = new OvertimeExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(idList);
		overtimeMapper.deleteByExample(example);
	}

	public List<Overtime> getOvertimesOfMonth(String year, String month) {
		OvertimeExample example = new OvertimeExample();
		Criteria criteria = example.createCriteria();
		criteria.andYearEqualTo(year).andMonthEqualTo(month);
		return overtimeMapper.selectByExampleWithUserAndEmp(example);
	}
}
