package org.lwl.demo.service.info.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.lwl.demo.common.InfoStatusEnum;
import org.lwl.demo.common.ex.BusinessException;
import org.lwl.demo.common.page.PageVo;
import org.lwl.demo.dao.RoleDao;
import org.lwl.demo.dto.RoleDto;
import org.lwl.demo.dto.RoleQueryDto;
import org.lwl.demo.model.Role;
import org.lwl.demo.service.info.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;
    @Override
    public PageVo<Role> getRolePage(RoleQueryDto roleQueryDto) {

//        QueryAction<Role> action = new QueryAction<Role>() {
//            @Override
//            public List<Role> executeQuery() throws Exception {
//                return roleDao.findRoleList(roleQueryDto);
//            }
//        };

//        QueryAction<Role> action = ()->roleDao.findRoleList(roleQueryDto);

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

        roleDao.deleteRole(ids);
    }

}
