package com.ghz.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistUser {

    private Integer id;
    private String username;
    private String nickName;
    private String role;
}
