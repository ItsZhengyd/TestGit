package com.example.demo;

import org.junit.jupiter.api.Test;

public class DebugTest {

    public static String str = "";

    @Test
    public void testController(){
        String a = "a";
        String b = "b";
        String c = a + b;
        str = "abc";
        String s = testService(testService2());
        System.out.println("=====");
    }

    public String testService(String str){
        String c = "";
        String d = "";
        System.out.println(str);

        return str;
    }

    public String testService2(){
        return "testService2";
    }

}
