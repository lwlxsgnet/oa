package org.lwl.demo.api.security;

import lombok.RequiredArgsConstructor;
import org.lwl.demo.common.currentuser.CurrentUser;
import org.lwl.demo.common.vo.R;
import org.lwl.demo.service.HomeService;
import org.lwl.demo.vo.MenuVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
