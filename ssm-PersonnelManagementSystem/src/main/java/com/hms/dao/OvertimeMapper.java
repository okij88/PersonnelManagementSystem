package com.hms.dao;

import com.hms.bean.Overtime;
import com.hms.bean.OvertimeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OvertimeMapper {
    long countByExample(OvertimeExample example);

    int deleteByExample(OvertimeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Overtime record);

    int insertSelective(Overtime record);

    List<Overtime> selectByExample(OvertimeExample example);
    
    List<Overtime> selectByExampleWithUserAndEmp(OvertimeExample example);

    Overtime selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Overtime record, @Param("example") OvertimeExample example);

    int updateByExample(@Param("record") Overtime record, @Param("example") OvertimeExample example);

    int updateByPrimaryKeySelective(Overtime record);

    int updateByPrimaryKey(Overtime record);
}