package org.lwl.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.lwl.demo.common.currentuser.CurrentUser;
import org.lwl.demo.dao.HomeDao;
import org.lwl.demo.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class OaServerApplicationTests {

    @Autowired
    private HomeDao homeDao;
    @Test
    void contextLoads() {
    }
    @Test
    public void testFindMenuList() {
        List<MenuVo> menuList = homeDao.findMenuList(new CurrentUser("admin", "用户1"));

        menuList.forEach(main->{
            log.debug("{}, {}", main.getMenuId(), main.getMenuName());
            main.getChildren().forEach(sub->{
                log.debug("   {}, {}, {}", sub.getMenuId(), sub.getMenuName(), sub.getMenuUrl());
            });
        });
    }
}
