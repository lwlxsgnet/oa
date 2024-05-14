package org.lwl.demo.api.security;

import jakarta.servlet.http.HttpServletRequest;
import org.lwl.demo.common.currentuser.CurrentUser;
import org.lwl.demo.common.token.TokenUser;
import org.lwl.demo.common.token.TokenUtils;
import org.lwl.demo.common.verifycode.VerifyCodeUtils;
import org.lwl.demo.common.vo.R;
import org.lwl.demo.service.security.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

    //@Autowired方式不加final 不是 spring 推荐写法
    @Autowired
    private LoginService loginService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private TokenUtils tokenUtils;
    @PostMapping("/security/login")
    //@ReuqestBody 处理前端POST请求传来的Json对象
    public R<?> login(@RequestBody Map<String, String> loginDto) {

        boolean ok = VerifyCodeUtils.checkVerifyCode(loginDto.get("u_verify_key"), loginDto.get("u_verify_code"));
        if (!ok) return R.err(R.CODE_ERR_BUSI, "验证码错误");

        CurrentUser currentuser = loginService.checkLogin(loginDto);

        String userIp = request.getRemoteAddr();

        TokenUser tokenUser = new TokenUser(currentuser.getUserId(), currentuser.getUserName(), userIp);

        String token = tokenUtils.loginSign(tokenUser, loginDto.get("u_pwd"));

        return R.OK(token);

    }
}
