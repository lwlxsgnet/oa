package org.lwl.demo.api;

import org.lwl.demo.common.vo.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;

@RestController
public class MyController {

    @GetMapping("/t")
    public R<?> test() {
        return R.OK("测试");
    }
}
