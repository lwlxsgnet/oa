package org.lwl.demo.service;

import org.lwl.demo.common.currentuser.CurrentUser;
import org.lwl.demo.vo.MenuVo;

import java.util.List;
import java.util.Map;

public interface HomeService {
    List<MenuVo> getMenuList(CurrentUser currentUser);

    void updatePassword(Map<String, String> pwdDto);
}
