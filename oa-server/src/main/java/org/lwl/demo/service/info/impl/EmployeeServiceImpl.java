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

import java.util.List;

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
        id = employeeDao.findMaxNumderID();
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
        employeeDao.changeStatus(id, status);
    }
}
