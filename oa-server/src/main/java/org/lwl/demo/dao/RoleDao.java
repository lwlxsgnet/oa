package org.lwl.demo.dao;

import org.apache.ibatis.annotations.*;
import org.lwl.demo.dto.RoleDto;
import org.lwl.demo.dto.RoleQueryDto;
import org.lwl.demo.model.Role;

import java.util.List;

@Mapper
public interface RoleDao {
    List<Role> findRoleList(RoleQueryDto roleQueryDto);

    @Select("select ifnull(max(ro_id), 0) from t_role ")
    int findMaxId();

    @Insert("insert into t_role(ro_id, ro_name) values(#{ro_id}, #{ro_name})")
    void insertRole(RoleDto roleDto);

    @Update("update t_role set ro_name = #{ro_name} where ro_id = #{ro_id}")
    void updateRole(RoleDto roleDto);


    void deleteRole(@Param("ids") Integer[] ids);

}
