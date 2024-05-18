package org.lwl.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private String u_id;
    private String u_name;
    private List<String> roleNameList;
}
