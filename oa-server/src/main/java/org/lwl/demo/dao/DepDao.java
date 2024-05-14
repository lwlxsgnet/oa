package org.lwl.demo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.lwl.demo.dto.DepDto;
import org.lwl.demo.dto.DepQueryDto;
import org.lwl.demo.model.Dep;

import java.util.List;

@Mapper
public interface DepDao {
    List<Dep> findDepList(DepQueryDto depQueryDto);

    @Select("select ifnull(max(d_id), 0) from t_dep ")
    int findMaxId();

    @Insert("insert into t_dep(d_id, d_name, d_remark, d_status) values(#{d_id}, #{d_name}, #{d_remark}, #{d_status})")
    void insertDep(DepDto depDto);

    @Update("update t_dep set d_name = #{d_name}, d_remark = #{d_remark} where d_id = #{d_id}")
    void updateDep(DepDto depDto);
}
