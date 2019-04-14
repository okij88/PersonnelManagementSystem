package com.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.bean.Attendance;
import com.hms.bean.AttendanceExample;
import com.hms.bean.AttendanceExample.Criteria;
import com.hms.dao.AttendanceMapper;

@Service
public class AttendanceService {
	@Autowired
	private AttendanceMapper attendanceMapper;

	public Attendance getAttendance(Integer id) {
		// TODO Auto-generated method stub
		return attendanceMapper.selectByPrimaryKey(id);
	}

	public List<Attendance> getAll() {
		// TODO Auto-generated method stub
		return attendanceMapper.selectByExample(null);
	}

	public void saveAttendance(Attendance attendance) {
		// TODO Auto-generated method stub
		attendanceMapper.insertSelective(attendance);
	}

	public void deleteAttendance(Integer id) {
		// TODO Auto-generated method stub
		attendanceMapper.deleteByPrimaryKey(id);
	}

	public void updateAttendance(Attendance attendance) {
		// TODO Auto-generated method stub
		attendanceMapper.updateByPrimaryKeySelective(attendance);
	}
	
	public void clear() {
		attendanceMapper.truncateTable();
	}

}
