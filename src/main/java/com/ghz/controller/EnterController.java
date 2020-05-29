package com.ghz.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ghz.bean.AnalysisKnifeData;
import com.ghz.bean.DistEnterBossData;
import com.ghz.bean.mg.Boss;
import com.ghz.bean.mg.BossList;
import com.ghz.bean.mg.Member;
import com.ghz.enumeration.StatusCodeEnum;
import com.ghz.except.GhzException;
import com.ghz.service.EnterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.ghz.utils.DistUtils.*;

@Api("数据录入接口")
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/enter")
public class EnterController {

    @Autowired
    private EnterService enterService;

    @ApiOperation("获取当前BOSS数据")
    @GetMapping("/getBossSituationData")
    public String getBossSituationData(){
        DistEnterBossData data;

        try {
            data = enterService.getBossSituationData();
        } catch (GhzException ge) {
            return putCode2JsonStr(ge);
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.UNKNOWN_ERROR,e);
        }

        return successAndRenderData(data);
    }

    @ApiOperation("获取出刀数据")
    @GetMapping("/getKnifeData")
    public String getKnifeData(int page,int limit){
        Map<String,Object> data;

        try {
            data = enterService.findPageKnifeData(page,limit);
        } catch (GhzException ge) {
            return putCode2JsonStr(ge);
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.UNKNOWN_ERROR,e);
        }

        return putCode2JsonStr(data,StatusCodeEnum.SUCCESS);
    }

    @ApiOperation("删除出刀数据")
    @PostMapping("/deleteKnifeData")
    public String getKnifeData(@RequestBody JSONObject body){

        Integer id;

        try {
            id = body.getInteger("id");
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.PARAM_EXCEPTION,e);
        }

        try {
            enterService.deleteKnifeData(id);
        } catch (GhzException ge) {
            return putCode2JsonStr(ge);
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.UNKNOWN_ERROR,e);
        }

        return successAndRender();
    }

    @ApiOperation("获取当前所有BOSS数据")
    @GetMapping("/getCurrentBossList")
    public String getCurrentBossList(){
        BossList bossList;
        List<Boss> bosses;

        try {
            bossList = enterService.getCurrentBossList();
            bosses = enterService.getCurrentBosses(bossList.getId());
        } catch (GhzException ge) {
            return putCode2JsonStr(ge);
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.UNKNOWN_ERROR,e);
        }

        Map<String,Object> data = new HashMap<>();
        data.put("ghzData",bossList);
        data.put("bossList",bosses);
        return successAndRenderData(data);
    }

    @ApiOperation("更新BOSS数据")
    @PostMapping("/updateBossData")
    public String updateBossData(@RequestBody JSONObject body){
        JSONArray ghzDataTableData = body.getJSONArray("ghzDataTableData");
        JSONArray currentBossListTableData = body.getJSONArray("currentBossListTableData");

        Integer round,currentNum;
        Date startDate;
        List<Boss> bosses;
        try {
            JSONObject ghzData = ghzDataTableData.getJSONObject(0);
            round = ghzData.getInteger("round");
            currentNum = ghzData.getInteger("currentNum");
            startDate = ghzData.getDate("startDate");
            bosses = currentBossListTableData.toJavaList(Boss.class);
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.PARAM_EXCEPTION,e);
        }

        try{
            enterService.updateBossData(round,currentNum,startDate,bosses);
        } catch (GhzException ge) {
            return putCode2JsonStr(ge);
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.UNKNOWN_ERROR,e);
        }

        return successAndRender();
    }

    @ApiOperation("列出所有公会成员")
    @GetMapping("/getAllMembers")
    public String getAllMembers(){
        List<Member> data;

        try {
            data = enterService.getAllMembers();
        } catch (GhzException ge) {
            return putCode2JsonStr(ge);
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.UNKNOWN_ERROR,e);
        }

        return successAndRenderData(data);
    }

    @ApiOperation("添加成员")
    @PostMapping("/addMember")
    public String addMember(@RequestBody JSONObject body){
        String numberId,nickName;
        try {
            numberId = body.getString("numberId");
            nickName = body.getString("nickName");
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.PARAM_EXCEPTION,e);
        }

        try {
            enterService.addMember(numberId,nickName);
        } catch (GhzException ge) {
            return putCode2JsonStr(ge);
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.UNKNOWN_ERROR,e);
        }

        return successAndRender();
    }

    @ApiOperation("删除")
    @PostMapping("/deleteMember")
    public String deleteMember(@RequestBody JSONObject body){
        int id;
        try {
            id = body.getInteger("id");
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.PARAM_EXCEPTION,e);
        }

        try {
            enterService.deleteMember(id);
        } catch (GhzException ge) {
            return putCode2JsonStr(ge);
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.UNKNOWN_ERROR,e);
        }

        return successAndRender();
    }

    @ApiOperation("出刀数据上传")
    @PostMapping("/insertKnife")
    public String insertKnife(@RequestBody JSONObject body){
        int memberId,damage;
        Date date;

        try {
            memberId = body.getInteger("membersSelect");
            damage = body.getInteger("damageInput");
            date = body.getDate("knifeDate");
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.PARAM_EXCEPTION,e);
        }

        try {
            enterService.insertKnife(memberId,damage,date);
        } catch (GhzException ge) {
            return putCode2JsonStr(ge);
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.UNKNOWN_ERROR,e);
        }

        return successAndRender();
    }

    @ApiOperation("出刀数据解析")
    @PostMapping("/knifeDataAnalysis")
    public String knifeDataAnalysis(@RequestBody JSONObject body){
        List<String> list = new ArrayList<>(Arrays.asList(body.getString("data").split("\n")));
        list.removeIf(item -> item == null || item.equals(""));

        Map<String, Object> data;
        try {
            data = enterService.knifeDataAnalysis(list);
        } catch (GhzException ge) {
            return putCode2JsonStr(ge);
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.UNKNOWN_ERROR,e);
        }

        return successAndRenderData(data);
    }

    @ApiOperation("解析数据上传")
    @PostMapping("/knifeDataAnalysisUpload")
    public String knifeDataAnalysisUpload(@RequestBody JSONObject body){
        DistEnterBossData bossData;
        List<AnalysisKnifeData> knifeDataList;
        try {
            bossData = JSONObject.parseObject(body.getJSONArray("bossData").getJSONObject(0).toString(), DistEnterBossData.class);
            knifeDataList = JSONArray.parseArray(body.getJSONArray("knifeData").toString(), AnalysisKnifeData.class);
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.PARAM_EXCEPTION,e);
        }

        try {
            enterService.knifeDataAnalysisUpload(bossData,knifeDataList);
        } catch (GhzException ge) {
            return putCode2JsonStr(ge);
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.UNKNOWN_ERROR,e);
        }
        return successAndRender();
    }
}
