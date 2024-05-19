package org.lwl.demo.dto;

import lombok.Data;
import org.lwl.demo.common.page.PageParamDto;

@Data
// 页面提交的模糊查询 Integer 改为 String 类型
public class LeaveQueryDto extends PageParamDto {
    private String l_id;
    private String e_id; //申请人员工编号
    private String l_cause;

    // 状态只是下拉框的查询
    private Integer l_status;

    private String leader_id; // 领导id(即领导的员工编号)
    private String e_name; // 请假人姓名
}
