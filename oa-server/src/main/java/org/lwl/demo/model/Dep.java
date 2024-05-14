package org.lwl.demo.model;

import lombok.Data;

@Data
//从数据库查询的实体类封装
public class Dep {
    //包装类存储对象的引用
    private Integer d_id;//用包装类是为了接受查询出来是 null 的数据
    private String d_name;
    private String d_remark;
    private Integer d_status;
}
