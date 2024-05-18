package org.lwl.demo.service.security;

import org.lwl.demo.common.page.PageVo;
import org.lwl.demo.dto.RoleDto;
import org.lwl.demo.dto.RoleQueryDto;
import org.lwl.demo.model.Role;
import org.lwl.demo.vo.MenuVo;

import java.util.List;
import java.util.Map;

public interface RoleService {
    PageVo<Role> getRolePage(RoleQueryDto roleQueryDto);

    void addRole(RoleDto roleDto);

    void updateRole(RoleDto roleDto);

    void deleteRole(Integer... ids);//为了处理多删除，使用可变长参数

    List<MenuVo> getAllFunList();

    List<Integer> getRoleFunIdsList(Integer roleId);

    void updateRoleFun(Map<String, Object> map);
}
