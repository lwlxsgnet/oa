package org.lwl.demo.dto;

import lombok.Data;
import org.lwl.demo.common.page.PageParamDto;

@Data
//负责接收页面查询条件的封装类
public class DepQueryDto extends PageParamDto {
    private String d_id; //方便模糊查询
    private String d_name;
    private String d_remark;
    private Integer d_status;
}
