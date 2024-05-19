package org.lwl.demo.api.info;

import lombok.RequiredArgsConstructor;
import org.lwl.demo.common.page.PageVo;
import org.lwl.demo.common.vo.R;
import org.lwl.demo.dto.EmpDto;
import org.lwl.demo.dto.EmpQueryDto;
import org.lwl.demo.model.Dep;
import org.lwl.demo.model.Employee;
import org.lwl.demo.service.info.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/info/emp/dep")
    public R<List<Dep>> allDepList() {
        List<Dep> list = employeeService.getALlEnableEmpList();
        return R.OK(list);
    }

    @GetMapping("/info/emp")
    //获取PageVo分页数据
    public R<PageVo<Employee>> empList(EmpQueryDto empQueryDto) {
        PageVo<Employee> page = employeeService.getEmployeePage(empQueryDto);
        return R.OK(page);
    }

    @PostMapping("/info/emp")
    public R<?> execAdd(@RequestBody EmpDto empDto) {
        employeeService.addEmp(empDto);
        return R.OK();
    }

    @PutMapping("/info/emp")
    public R<?> execUpd(@RequestBody EmpDto empDto) {
        employeeService.updateEmp(empDto);
        return R.OK();
    }

    @DeleteMapping("/info/emp/{id}")
    public R<?> execDel(@PathVariable String id) {
        employeeService.deleteEmp(id);
        return R.OK();
    }

    @DeleteMapping("/info/emp")
    public R<?> execDel(@RequestBody String[] ids) {
        employeeService.deleteEmp(ids);
        return R.OK();
    }

    @PutMapping("/info/emp/{id}/{status}")
    public R<?> changeStatus(@PathVariable String id, @PathVariable Integer status) {
        employeeService.changeStatus(id, status);
        return R.OK();
    }

    @PostMapping("/info/emp/user")
    public R<?> createUser(@RequestBody Map<String, String> userIdMap) {
        employeeService.createUser(userIdMap);
        return R.OK();
    }

    @DeleteMapping("/info/emp/user")
    public R<?> cancelUser(@RequestBody Map<String, String> userIdMap) {
        employeeService.cancelUser(userIdMap);
        return R.OK();
    }

    @PutMapping("/info/emp/leader")
    public R<?> setLeader(@RequestBody Map<String, String> empIdMap) {
        employeeService.setLeader(empIdMap);
        return R.OK();
    }
}
