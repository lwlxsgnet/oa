package org.lwl.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.lwl.demo.common.currentuser.CurrentUser;
import org.lwl.demo.vo.MenuVo;

import java.util.List;

@Mapper
public interface HomeDao {
    List<MenuVo> findMenuList(CurrentUser currentUser);
}
