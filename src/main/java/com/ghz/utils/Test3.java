package com.ghz.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test3 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("aaa123aaa");
        list.add("bbb123aaa");
        list.add("aaa123ccc");
        list.add("ddd1e3aaa");
        list.add("aaa1eee");

        List<String> list2 = list.subList(0,2);
        list2.set(0,"123456");
        System.out.println(list2);
        System.out.println(list);

    }
}
