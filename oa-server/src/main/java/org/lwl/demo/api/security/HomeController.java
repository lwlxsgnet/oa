package org.lwl.demo.api.security;

import lombok.RequiredArgsConstructor;
import org.lwl.demo.common.currentuser.CurrentUser;
import org.lwl.demo.common.vo.R;
import org.lwl.demo.service.security.HomeService;
import org.lwl.demo.vo.MenuVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    //当前登录用户(该对象实际上是当前用户代理，内部所代理实际用户随真是用户不同而改变)
    private final CurrentUser currentUser;

    @GetMapping("/security/home/menus")
    public R<List<MenuVo>> getMenus() {
        List<MenuVo> mainMenuList = homeService.getMenuList(currentUser);
        return R.OK(mainMenuList);
    }

    @GetMapping("/security/home/currentuser")
    public R<CurrentUser> getCurrentUser() {
        return R.OK(currentUser);
    }

    @Value("${oa.user-avatar-dir}")
    private String userAvatarDir;

    @PostMapping("/security/home/avatar")
    public R<?> uploadAvatar(MultipartFile avatar) throws IOException {
        File file = new File(userAvatarDir, currentUser.getUserId());
        FileCopyUtils.copy(avatar.getInputStream(), new FileOutputStream(file));
        return R.OK();
    }

    //为什么用 Put , 因为修改密码这个用户不变的，密码只是被覆盖掉了  符合幂等特性  而注册功能 每次用户都不一样 用Post 非幂等
    @PutMapping("/security/home/pwd")
    public R<?> updatePassword(@RequestBody Map<String, String> pwdDto) {
        pwdDto.put("userId", currentUser.getUserId());
        homeService.updatePassword(pwdDto);
        return R.OK();
    }
}
