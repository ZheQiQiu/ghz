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

        List<String> list2 = list.stream().filter(a-> a.matches("^.*123.*")).collect(Collectors.toList());

        System.out.println(list2);

    }
}
