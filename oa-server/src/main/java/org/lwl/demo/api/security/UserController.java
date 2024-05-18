package org.lwl.demo.api.security;

import lombok.RequiredArgsConstructor;
import org.lwl.demo.common.page.PageVo;
import org.lwl.demo.common.vo.R;
import org.lwl.demo.dto.UserDto;
import org.lwl.demo.dto.UserQueryDto;
import org.lwl.demo.model.Role;
import org.lwl.demo.model.User;
import org.lwl.demo.service.security.UserService;
import org.lwl.demo.vo.MenuVo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/security/user")
    public R<PageVo<User>> userList(UserQueryDto userQueryDto) {
        PageVo<User> page = userService.getUserPage(userQueryDto);
        return R.OK(page);
    }

    @PostMapping("/security/user")
    public R<?> execAdd(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
        return R.OK();
    }

    @PutMapping("/security/user")
    public R<?> execUpd(@RequestBody UserDto userDto) {
        userService.updateUser(userDto);
        return R.OK();
    }

    @DeleteMapping("/security/user/{id}")
    public R<?> execDel(@PathVariable String id) {
        userService.deleteUser(id);
        return R.OK();
    }

    @DeleteMapping("/security/user")
    public R<?> execDel(@RequestBody String[] ids) {
        userService.deleteUser(ids);
        return R.OK();
    }

    @GetMapping("/security/user/role")
    public R<List<Role>> allRoleList() {
        List<Role> list = userService.getAllRoleList();
        return R.OK(list);
    }

    @GetMapping("/security/user/role/{userId}")
    public R<List<Integer>> userRoleList(@PathVariable String userId) {
        List<Integer> roleIdsList =  userService.getUserRoleIdsList(userId);
        return R.OK(roleIdsList);
    }

    @PutMapping("/security/user/role")
    public R<?> execUpdRole(@RequestBody Map<String, Object> map) {
        userService.updateUserRole(map);
        return R.OK();
    }
}
