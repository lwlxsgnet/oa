package org.lwl.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.lwl.demo.common.InfoStatusEnum;

import java.util.Date;

@Data
public class Employee {
    private String e_id;
    private String e_name;
    private Integer e_sex;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date e_birth;
    private Integer e_status;
    private Integer d_id;
    private String d_name;

    // 工号是否是登录账号
    private boolean account;

    // 工号是否是部门领导账号
    private boolean leader;

    public String getE_status_name() {
        if (e_status == null) return null;
        return InfoStatusEnum.getTextByCode(e_status);//枚举类根据状态代码得到名字
    }
}
