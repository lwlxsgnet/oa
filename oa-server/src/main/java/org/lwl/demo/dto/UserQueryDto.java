package org.lwl.demo.dto;

import lombok.Data;
import org.lwl.demo.common.page.PageParamDto;

@Data
public class UserQueryDto extends PageParamDto {
    private String u_id;
    private String u_name;
}
