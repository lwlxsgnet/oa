package org.lwl.demo.service.security;

import org.lwl.demo.common.page.PageVo;
import org.lwl.demo.dto.UserDto;
import org.lwl.demo.dto.UserQueryDto;
import org.lwl.demo.model.Role;
import org.lwl.demo.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    PageVo<User> getUserPage(UserQueryDto userQueryDto);

    void addUser(UserDto userDto);

    void updateUser(UserDto userDto);

    void deleteUser(String... ids);//为了处理多删除，使用可变长参数

    List<Role> getAllRoleList();

    List<Integer> getUserRoleIdsList(String userId);

    void updateUserRole(Map<String, Object> map);
}
