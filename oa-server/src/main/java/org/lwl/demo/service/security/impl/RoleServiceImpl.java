package org.lwl.demo.service.security.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.lwl.demo.common.ex.BusinessException;
import org.lwl.demo.common.page.PageVo;
import org.lwl.demo.dao.RoleDao;
import org.lwl.demo.dto.RoleDto;
import org.lwl.demo.dto.RoleQueryDto;
import org.lwl.demo.model.Role;
import org.lwl.demo.service.security.RoleService;
import org.lwl.demo.vo.MenuVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;
    @Override
    public PageVo<Role> getRolePage(RoleQueryDto roleQueryDto) {

        // QueryAction<Role> action = new QueryAction<Role>() {
        //     @Override
        //     public List<Role> executeQuery() throws Exception {
        //         return roleDao.findRoleList(roleQueryDto);
        //     }
        // };
        //  QueryAction<Role> action = ()->roleDao.findRoleList(roleQueryDto);

        return PageVo.getPageVo(roleQueryDto, ()->roleDao.findRoleList(roleQueryDto));//第二个参数可用action替代
    }

    private int id;//最大编号

    @PostConstruct//表示该方法在创建对象后执行，且仅一次执行 初始化方法
    public void init() {
        id = roleDao.findMaxId();
    }
    //线程安全 synchronized
    private synchronized int newId() {
        return ++id;
    }

    @Override
    public void addRole(RoleDto roleDto) {
        roleDto.setRo_id(newId());
        roleDao.insertRole(roleDto);
    }

    @Override
    public void updateRole(RoleDto roleDto) {
        roleDao.updateRole(roleDto);
    }

    @Override
    public void deleteRole(Integer... ids) {
        if (ids == null || ids.length == 0) throw new BusinessException("请选择至少一条数据!");
        if (roleDao.findExistsUser(ids)) throw new BusinessException("存在角色已被用户使用,无法删除!");
        if (roleDao.findExistsFun(ids)) throw new BusinessException("存在角色已有权限,无法删除!");
        roleDao.deleteRole(ids);
    }

    @Override
    public List<MenuVo> getAllFunList() {
        return roleDao.findAllFunList();
    }

    @Override
    public List<Integer> getRoleFunIdsList(Integer roleId) {
        return roleDao.findRoleFunIdsList(roleId);
    }

    @Override
    public void updateRoleFun(Map<String, Object> map) {
        //提交的权限需要又删又增
        //统一先删再增
        //先删除指定角色的权限
        roleDao.deleteRoleFun(map);

        List<Integer> funIds = (List<Integer>) map.get("funIds");

        if (funIds != null && !funIds.isEmpty()){
            //再增加角色的新增加的权限
            roleDao.insertRoleFun(map);
        }
    }
}
