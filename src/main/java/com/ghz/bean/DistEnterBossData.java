package com.ghz.bean;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistEnterBossData {

    private Integer bossId;
    private Integer round;
    private Integer currentNum;
    private Integer currentHp;
    private Integer maxHp;
    private Date startDate;

    public DistEnterBossData(DistEnterBossData bd){
        bossId = bd.getBossId();
        round = bd.getRound();
        currentNum = bd.getCurrentNum();
        currentHp = bd.getCurrentHp();
        maxHp = bd.getMaxHp();
        startDate = bd.getStartDate();
    }

}
