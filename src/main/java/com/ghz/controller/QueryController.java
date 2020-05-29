package com.ghz.controller;

import com.ghz.bean.HandsontableColumn;
import com.ghz.enumeration.StatusCodeEnum;
import com.ghz.except.GhzException;
import com.ghz.service.QueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ghz.utils.DistUtils.putCode2JsonStr;
import static com.ghz.utils.DistUtils.successAndRenderData;

@Api("数据查询接口")
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/query")
public class QueryController {

    @Autowired
    private QueryService queryService;

    @GetMapping("testGetColumns")
    public String testGetColumns(){
        List<HandsontableColumn> columns = new ArrayList<>();
        HandsontableColumn c1 = new HandsontableColumn();
        HandsontableColumn c2 = new HandsontableColumn();

        c1.setData("");
        c1.setTitle("<b>col1</b>");
        c1.setType("text");
        c1.setReadOnly(true);
        c1.setWidth(160);

        c2.setData("");
        c2.setTitle("<b>col2</b>");
        c2.setType("text");
        c2.setReadOnly(false);
        c2.setWidth(160);

        columns.add(c1);
        columns.add(c2);

        return successAndRenderData(columns);
    }

    @ApiOperation("获取表格列以及数据")
    @GetMapping("/getColAndAllGhzData")
    public String getColAndAllGhzData(){

        Map<String,Object> data;

        try {
            data = queryService.getColAndAllGhzData();
        } catch (GhzException ge) {
            return putCode2JsonStr(ge);
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.UNKNOWN_ERROR,e);
        }

        return successAndRenderData(data);
    }
}
