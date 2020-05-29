package com.ghz.controller;

import com.alibaba.fastjson.JSONObject;
import com.ghz.bean.DateKnifeData;
import com.ghz.enumeration.StatusCodeEnum;
import com.ghz.except.GhzException;
import com.ghz.service.KnifeStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static com.ghz.utils.DistUtils.putCode2JsonStr;
import static com.ghz.utils.DistUtils.successAndRenderData;

@Api("出刀统计")
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/knife")
public class KnifeStatisticsController {

    @Autowired
    private KnifeStatisticsService knifeStatisticsService;

    @ApiOperation("根据日期统计出刀数据")
    @PostMapping("/dateKnifeData")
    public String dateKnifeData(@RequestBody JSONObject jsonObject){

        Date date = jsonObject.getDate("date");
        if(date == null) return putCode2JsonStr(StatusCodeEnum.PARAM_EXCEPTION);

        List<DateKnifeData> data;
        try {
            data = knifeStatisticsService.dateKnifeData(date);
        } catch (GhzException ge) {
            return putCode2JsonStr(ge);
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.UNKNOWN_ERROR,e);
        }

        System.out.println(successAndRenderData(data));
        return successAndRenderData(data);

    }
}
