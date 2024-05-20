package org.lwl.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.lwl.demo.dto.LeaveDto;
import org.lwl.demo.dto.LeaveQueryDto;
import org.lwl.demo.model.Leave;

import java.util.List;

@Mapper
public interface ExamDao {

    List<Leave> findExamList(LeaveQueryDto leaveQueryDto);

    @Update("update t_leave set l_status = #{l_status}, l_spr = #{l_spr}, l_sp_date = #{l_sp_date}, l_sp_advice = #{l_sp_advice} where l_id = #{l_id}")
    void updateLeave(LeaveDto leaveDto);
}
