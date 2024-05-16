package org.lwl.demo.dao;

import org.apache.ibatis.annotations.*;
import org.lwl.demo.dto.EmpDto;
import org.lwl.demo.dto.EmpQueryDto;
import org.lwl.demo.model.Dep;
import org.lwl.demo.model.Employee;

import java.util.List;

@Mapper
public interface EmployeeDao {

    List<Employee> findEmployeeList(EmpQueryDto empQueryDto);

    @Select("select d_id, d_name from t_dep where d_status = 2")
    List<Dep> findAllEnaleDepList();

    @Select("select ifnull(max(cast(e_id as unsigned)), 0) from t_emp")
    int findMaxNumderID();

    @Insert("insert into t_emp(e_id, d_id, e_name, e_sex, e_birth, e_status) values(#{e_id}, #{d_id}, #{e_name}, #{e_sex}, #{e_birth}, #{e_status})")
    void insertEmp(EmpDto empDto);

    @Update("update t_emp set e_name = #{e_name}, e_sex = #{e_sex}, e_birth = #{e_birth}, d_id = #{d_id} where e_id = #{e_id}")
    void updateEmp(EmpDto empDto);

    void deleteEmp(String[] ids);

    Boolean findExistsUndetermined(@Param("ids") String... ids);

    @Update("update t_emp set e_status = #{status} where e_id = #{id}")
    void changeStatus(@Param("id") String id, @Param("status") Integer status);
}
