package org.lwl.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.lwl.demo.common.currentuser.CurrentUser;
import org.lwl.demo.common.ex.BusinessException;
import org.lwl.demo.dao.HomeDao;
import org.lwl.demo.service.HomeService;
import org.lwl.demo.vo.MenuVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final HomeDao homeDao;
    @Override
    public List<MenuVo> getMenuList(CurrentUser currentUser) {

        return homeDao.findMenuList(currentUser);
    }

    @Override
    public void updatePassword(Map<String, String> pwdDto) {
        String oldPwd = homeDao.findOldPwd(pwdDto);
        if (!(oldPwd.equals(pwdDto.get("old_pwd")))) throw new BusinessException("原密码输入错误!");
        homeDao.updatePassword(pwdDto);
    }
}
