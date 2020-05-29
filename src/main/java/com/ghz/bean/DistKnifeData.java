package com.ghz.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistKnifeData {

    private Integer id;
    private String nickName;
    private Integer damage;
    private Integer round;
    private Integer bossOrdered;
    private String date;
}
