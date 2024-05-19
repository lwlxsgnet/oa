package org.lwl.demo.dao;

import org.apache.ibatis.annotations.*;
import org.lwl.demo.dto.UserDto;
import org.lwl.demo.dto.UserQueryDto;
import org.lwl.demo.model.Role;
import org.lwl.demo.model.User;
import org.lwl.demo.vo.MenuVo;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {
    List<User> findUserList(UserQueryDto userQueryDto);

    // 自动生成用户账号格式： U00001. U00002
    //  substr 按 u_id 从第二位取至末尾 convert 将取到到值转为无符号整数    instr 以U开头的数据
    @Select("select ifnull(max(convert(substr(u_id, 2), unsigned)), 0) from t_user where instr(u_id, 'U') = 1 ")
    int findMaxId();

    //密码写死
    @Insert("insert into t_user(u_id, u_name, u_pwd) values(#{u_id}, #{u_name}, '123456')")
    void insertUser(UserDto userDto);

    @Update("update t_user set u_name = #{u_name} where u_id = #{u_id}")
    void updateUser(UserDto userDto);

    void deleteUser(@Param("ids") String[] ids);

    List<Role> findAllRoleList();

    @Select("select ro_id from t_ur where u_id = #{userId}")
    List<Integer> findUserRoleIdsList(String userId);

    @Delete("delete from t_ur where u_id = #{userId}") // roelId 参数名参考前端传过来的
    void deleteUserRole(Map<String, Object> map);

    void insertUserRole(Map<String, Object> map);

    //已分配角色的用户不允许删除 @Param 起别名
    boolean findExistsRole(@Param("userIds") String... ids);

    List<String> findAllNotNormalUser();
}
