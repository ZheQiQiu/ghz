package com.ghz.utils;

public class Test2 {
    public static void main(String[] args) {
//        String str = "当前2周目，1号boss生命值2000000";
        String str = "testId #1已强行下树";
//        System.out.println(str.matches("(^申请出刀$)|(^完成[ ])"));
//        System.out.println(str.matches("^(现在|当前)[0-9]*周目，[0-9]*号boss生命值[0-9]*$"));
//        System.out.println(str.replaceAll("^(现在|当前)|周目，|号boss生命值"," ").substring(1).split(" "));
        System.out.println(str.matches(".*#[0-9]*.*已完成挑战boss$|.*#[0-9]*.*已强行下树$"));
        System.out.println(Integer.parseInt(str.replaceAll("^.*#|[ ]*已完成挑战boss$|[ ]*已强行下树$","")));
    }
}
