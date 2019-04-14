package com.hms.dao;

import com.hms.bean.Leave;
import com.hms.bean.LeaveExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LeaveMapper {
    long countByExample(LeaveExample example);

    int deleteByExample(LeaveExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Leave record);

    int insertSelective(Leave record);

    List<Leave> selectByExample(LeaveExample example);
    
    List<Leave> selectByExampleWithUserAndEmp(LeaveExample example);

    Leave selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Leave record, @Param("example") LeaveExample example);

    int updateByExample(@Param("record") Leave record, @Param("example") LeaveExample example);

    int updateByPrimaryKeySelective(Leave record);

    int updateByPrimaryKey(Leave record);
}