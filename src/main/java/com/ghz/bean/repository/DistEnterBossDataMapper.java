package com.ghz.bean.repository;

import com.ghz.bean.DistEnterBossData;

import java.util.List;

public interface DistEnterBossDataMapper {
    List<DistEnterBossData> selectCurrentBossData();
}
