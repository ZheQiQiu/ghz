package com.ghz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Api("页面控制")
@Controller
public class PageController {

    @ApiOperation("首页")
    @GetMapping("/homePage")
    public String homePage(){
        return "homePage";
    }

    @ApiOperation("登录页面")
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
