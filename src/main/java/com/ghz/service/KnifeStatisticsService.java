package com.ghz.service;

import com.ghz.bean.DateKnifeData;
import com.ghz.except.GhzException;

import java.util.Date;
import java.util.List;

public interface KnifeStatisticsService {
    List<DateKnifeData> dateKnifeData(Date date) throws GhzException;
}
