package org.lwl.demo.api;

import org.lwl.demo.common.verifycode.VerifyCodeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;

@RestController
public class VerifyCodeController {
    @GetMapping("/res/verifycode")
    public void outVerifyCodeImage(String u_verify_key, OutputStream out) {
        VerifyCodeUtils.outVerifyCodeImage(u_verify_key,out);
    }
}
