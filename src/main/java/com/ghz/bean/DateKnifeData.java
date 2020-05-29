package com.ghz.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateKnifeData {

    private Date date;
    private String nickName;
    private Integer count;
    private Integer totalDamage;

}
