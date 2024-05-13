package org.lwl.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.lwl.demo.common.currentuser.CurrentUser;
import org.lwl.demo.dao.HomeDao;
import org.lwl.demo.service.HomeService;
import org.lwl.demo.vo.MenuVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final HomeDao homeDao;
    @Override
    public List<MenuVo> getMenuList(CurrentUser currentUser) {

        return homeDao.findMenuList(currentUser);
    }
}
