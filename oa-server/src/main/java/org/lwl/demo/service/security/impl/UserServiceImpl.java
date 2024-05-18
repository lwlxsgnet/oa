package org.lwl.demo.service.security.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.lwl.demo.common.ex.BusinessException;
import org.lwl.demo.common.page.PageVo;
import org.lwl.demo.dao.UserDao;
import org.lwl.demo.dto.UserDto;
import org.lwl.demo.dto.UserQueryDto;
import org.lwl.demo.model.Role;
import org.lwl.demo.model.User;
import org.lwl.demo.service.security.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    @Override
    public PageVo<User> getUserPage(UserQueryDto userQueryDto) {

//        QueryAction<User> action = new QueryAction<User>() {
//            @Override
//            public List<User> executeQuery() throws Exception {
//                return userDao.findUserList(userQueryDto);
//            }
//        };

//        QueryAction<User> action = ()->userDao.findUserList(userQueryDto);

        return PageVo.getPageVo(userQueryDto, ()->userDao.findUserList(userQueryDto));//第二个参数可用action替代
    }

    private int id;//最大编号

    @PostConstruct//表示该方法在创建对象后执行，且仅一次执行 初始化方法
    public void init() {
        id = userDao.findMaxId();
    }
    //线程安全 synchronized
    private synchronized String newId() {
        //参照 UserDao 获取 newId
        return String.format("U%05d", ++id);
    }

    @Override
    public void addUser(UserDto userDto) {
        userDto.setU_id(newId());
        userDao.insertUser(userDto);
    }

    @Override
    public void updateUser(UserDto userDto) {
        userDao.updateUser(userDto);
    }

    @Override
    public void deleteUser(String... ids) {

        if (ids == null || ids.length == 0) throw new BusinessException("请选择至少一条数据!");

        userDao.deleteUser(ids);
    }

    @Override
    public List<Role> getAllRoleList() {
        return userDao.findAllRoleList();
    }

    @Override
    public List<Integer> getUserRoleIdsList(String userId) {
        return userDao.findUserRoleIdsList(userId);
    }

    @Override
    public void updateUserRole(Map<String, Object> map) {
        //提交的权限需要又删又增
        //统一先删再增
        //先删除指定角色的权限
        userDao.deleteUserRole(map);

        //测试BUG 全部不选的话会报错 删完没东西增加了
        List<Integer> roleIds = (List<Integer>) map.get("roleIds");
        if (roleIds != null && roleIds.size() > 0){
            //再增加角色的新增加权限
            userDao.insertUserRole(map);
        }
    }
}
