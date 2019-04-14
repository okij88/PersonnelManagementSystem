package com.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.bean.Job;
import com.hms.bean.JobExample;
import com.hms.dao.JobMapper;

@Service
public class JobService {
	@Autowired
	private JobMapper jobMapper;

	public List<Job> getAll() {
		// TODO Auto-generated method stub
		return jobMapper.selectByExample(null);
	}

	public void saveJob(Job job) {
		// TODO Auto-generated method stub
		jobMapper.insertSelective(job);
	}

	public boolean checkname(String name) {
		// TODO Auto-generated method stub
		JobExample example = new JobExample();
		com.hms.bean.JobExample.Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(name);
		long count =jobMapper.countByExample(example);
		return count == 0;
	}

	public void deleteJob(Integer id) {
		// TODO Auto-generated method stub
		jobMapper.deleteByPrimaryKey(id);
	}

	public void deleteJobs(List<Integer> idList) {
		// TODO Auto-generated method stub
		JobExample example = new JobExample();
		com.hms.bean.JobExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(idList);
		jobMapper.deleteByExample(example);
	}

	public Job getJob(Integer id) {
		// TODO Auto-generated method stub
		return jobMapper.selectByPrimaryKey(id);
	}

	public void updateJob(Job job) {
		// TODO Auto-generated method stub
		jobMapper.updateByPrimaryKeySelective(job);
	}

}
