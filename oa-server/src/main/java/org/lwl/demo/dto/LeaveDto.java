package org.lwl.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
// 封装页面提交的新增修改的数据
public class LeaveDto {
    private long l_id;

    private String e_id;
    // 提交的是Json数据格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date l_submit_date;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    private Date l_start;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    private Date l_end;

    private String l_cause;

    private Integer l_status;

    private String l_spr;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date l_sp_date;

    private String l_sp_advice;
}
