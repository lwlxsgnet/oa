package org.lwl.demo.service.info;

import org.lwl.demo.common.page.PageVo;
import org.lwl.demo.dto.EmpDto;
import org.lwl.demo.dto.EmpQueryDto;
import org.lwl.demo.model.Dep;
import org.lwl.demo.model.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    PageVo<Employee> getEmployeePage(EmpQueryDto empQueryDto);
    List<Dep> getALlEnableEmpList();
    void addEmp(EmpDto empDto);
    void updateEmp(EmpDto empDto);
    void deleteEmp(String... ids);
    void changeStatus(String id, Integer status);

    void createUser(Map<String, String> userIdMap);

    void cancelUser(Map<String, String> userIdMap);

    void setLeader(Map<String, String> empIdMap);
}
