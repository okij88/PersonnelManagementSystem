package com.hms.dao;

import com.hms.bean.Workday;
import com.hms.bean.WorkdayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WorkdayMapper {
    long countByExample(WorkdayExample example);

    int deleteByExample(WorkdayExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Workday record);

    int insertSelective(Workday record);
    
    int insertBatch(@Param("list")List<Workday> list);

    List<Workday> selectByExample(WorkdayExample example);

    Workday selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Workday record, @Param("example") WorkdayExample example);

    int updateByExample(@Param("record") Workday record, @Param("example") WorkdayExample example);

    int updateByPrimaryKeySelective(Workday record);

    int updateByPrimaryKey(Workday record);
    
    int truncateTable();
}