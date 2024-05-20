package org.lwl.demo.service.info.impl;

import jakarta.annotation.PostConstruct;
import org.lwl.demo.common.InfoStatusEnum;
import org.lwl.demo.common.ex.BusinessException;
import org.lwl.demo.common.page.PageVo;
import org.lwl.demo.dao.EmployeeDao;
import org.lwl.demo.dto.EmpDto;
import org.lwl.demo.dto.EmpQueryDto;
import org.lwl.demo.model.Dep;
import org.lwl.demo.model.Employee;
import org.lwl.demo.service.info.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;

    @Override
    public List<Dep> getALlEnableEmpList() {
        return employeeDao.findAllEnaleDepList();
    }

    @Override
    public PageVo<Employee> getEmployeePage(EmpQueryDto empQueryDto) {
        return PageVo.getPageVo(empQueryDto, ()->employeeDao.findEmployeeList(empQueryDto));
    }

    private int id; //最大编号

    @PostConstruct //表示该方法在创建对象后执行，且仅一次执行 初始化方法
    public void init() {
        id = employeeDao.findMaxNumberID();
    }

    private synchronized String newId() {
        return String.format("%05d", ++id);
    }

    @Override
    public void addEmp(EmpDto empDto) {
        empDto.setE_id(newId());
        empDto.setE_status(InfoStatusEnum.undetermined.getCode());
        employeeDao.insertEmp(empDto);
    }

    @Override
    public void updateEmp(EmpDto empDto) {
        employeeDao.updateEmp(empDto);
    }

    @Override
    public void deleteEmp(String... ids) {
        if (ids == null || ids.length == 0) throw new BusinessException("请选择至少一条数据!");

        //如果被删数据状态确定 那么需要阻止其被删除
        Boolean exists = employeeDao.findExistsUndetermined(ids);
        if (exists) throw new BusinessException("已确定,禁止删除!");

        employeeDao.deleteEmp(ids);
    }

    @Override
    public void changeStatus(String id, Integer status) {
        // 如果status为禁用状态，那么还需要进行撤销账号操作 另外如果设置了领导，同时领导账号也得禁用
        if (InfoStatusEnum.disabled.getCode().equals(status)) {
            Map<String, String> empIdMap = new HashMap<>();
            empIdMap.put("empId", id);
            this.cancelUser(empIdMap);  // 删除生成账号

            employeeDao.deleteLeaderByEmpId(id); //删除领导
        }
        employeeDao.changeStatus(id, status);
    }

    @Override
    public void createUser(Map<String, String> userIdMap) {
        employeeDao.insertUserByEmpId(userIdMap);
    }

    @Override
    public void cancelUser(Map<String, String> userIdMap) {
        // 直接删除用户删除报错， 是因为用户已有角色
        // 那么需要删除用户的时候也把相应的角色删除掉
        employeeDao.deleteUserRole(userIdMap);
        employeeDao.deleteUserByEmpId(userIdMap);
    }

    @Override
    public void setLeader(Map<String, String> empIdMap) {
        // 部门只能有一个领导，先删后增
        employeeDao.deleteLeader(empIdMap);
        employeeDao.insertLeader(empIdMap);
    }
}
