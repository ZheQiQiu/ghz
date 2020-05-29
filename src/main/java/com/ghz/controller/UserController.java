package com.ghz.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ghz.bean.DistUser;
import com.ghz.bean.mg.Member;
import com.ghz.bean.mg.User;
import com.ghz.enumeration.StatusCodeEnum;
import com.ghz.except.GhzException;
import com.ghz.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.ghz.utils.DistUtils.*;

@Api("用户相关接口")
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("查询所有用户")
    @GetMapping("/listAllUser")
    public String listAllUser(){
        List<DistUser> data;

        try {
            data = userService.selectAllUserAndRole();
        } catch (GhzException ge) {
            return putCode2JsonStr(ge);
        }

        return successAndRenderData(data);
    }

    @ApiOperation("获取当前用户昵称")
    @GetMapping("/getNickName")
    public String getNickName(HttpSession session){
        return successAndRenderData(((User)session.getAttribute("user")).getNickName());
    }

    @ApiOperation("修改会员")
    @PostMapping("/updateMember")
    public String updateMember(@RequestBody JSONObject jsonObject){
        Member member;
        try {
            member = JSONObject.parseObject(jsonObject.toString(), Member.class);
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.PARAM_EXCEPTION,e);
        }

        try {
            userService.updateMember(member);
        } catch (GhzException ge) {
            return putCode2JsonStr(ge);
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.UNKNOWN_ERROR,e);
        }

        return successAndRender();
    }

    @ApiOperation("登录接口")
    @PostMapping("/login")
    public String login(@RequestBody JSONObject jsonObject, HttpSession session){

        String username,password;
        try {
            username = jsonObject.getString("username");
            password = jsonObject.getString("password");
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.PARAM_EXCEPTION,e);
        }

        try {
            userService.userLogin(username,password,session);
        } catch (GhzException ge) {
            return putCode2JsonStr(ge);
        } catch (Exception e) {
            return putCode2JsonStr(StatusCodeEnum.UNKNOWN_ERROR,e);
        }
        return successAndRender();
    }
}
