package org.lwl.demo.api.security;

import lombok.RequiredArgsConstructor;
import org.lwl.demo.common.page.PageVo;
import org.lwl.demo.common.vo.R;
import org.lwl.demo.dto.RoleDto;
import org.lwl.demo.dto.RoleQueryDto;
import org.lwl.demo.model.Role;
import org.lwl.demo.service.info.RoleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/security/role")
    public R<PageVo<Role>> roleList(RoleQueryDto roleQueryDto) {
        PageVo<Role> page = roleService.getRolePage(roleQueryDto);
        return R.OK(page);
    }

    @PostMapping("/security/role")
    public R<?> execAdd(@RequestBody RoleDto roleDto) {
        roleService.addRole(roleDto);
        return R.OK();
    }

    @PutMapping("/security/role")
    public R<?> execUpd(@RequestBody RoleDto roleDto) {
        roleService.updateRole(roleDto);
        return R.OK();
    }

    @DeleteMapping("/security/role/{id}")
    public R<?> execDel(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return R.OK();
    }

    @DeleteMapping("/security/role")
    public R<?> execDel(@RequestBody Integer[] ids) {
        roleService.deleteRole(ids);
        return R.OK();
    }
}