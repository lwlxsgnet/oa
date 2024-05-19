package org.lwl.demo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.lwl.demo.dto.LeaveDto;
import org.lwl.demo.dto.LeaveQueryDto;
import org.lwl.demo.model.Leave;

import java.util.List;

@Mapper
public interface ApprevDao {
    List<Leave> findApprevList(LeaveQueryDto leaveQueryDto);

    @Insert("insert into t_leave(e_id, l_cause, l_start, l_end, l_status, l_submit_date)" +
            "values(#{e_id}, #{l_cause}, #{l_start}, #{l_end}, #{l_status}, #{l_submit_date})")
    void insertLeave(LeaveDto leaveDto);

    @Update("update t_leave set l_cause = #{l_cause}, l_start = #{l_start}, l_end = #{l_end}, l_submit_date = #{l_submit_date} where l_id = #{l_id}")
    void updateLeave(LeaveDto leaveDto);

    boolean findExistsSubmited(@Param("l_ids") Long... ids);

    void deleteLeave(@Param("l_ids") Long... ids);

    @Update("update t_leave set l_status = #{l_status}, l_submit_date = now() where l_id = #{l_id}")
    void updateLeaveStatus(@Param("l_id") Long id, @Param("l_status") int code);
}
