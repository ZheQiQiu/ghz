package com.ghz.utils;

import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws ParseException {
        String str = "quentina #1 2020/5/23 14:09:08";
        System.out.println(str.matches("^.*#.*/.*/.*:.*:.*[0-9]$"));
        String[] arr = str.substring(str.indexOf('#')+1).split(" ");
        System.out.println(arr[0]);
        System.out.println(new SimpleDateFormat("yyyy/MM/dd").parse(arr[1]));
    }
}
