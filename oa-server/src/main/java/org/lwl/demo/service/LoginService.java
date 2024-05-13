package org.lwl.demo.service;


import org.lwl.demo.common.currentuser.CurrentUser;

import java.util.Map;

public interface LoginService {

    CurrentUser checkLogin(Map<String, String> loginDto);
}
