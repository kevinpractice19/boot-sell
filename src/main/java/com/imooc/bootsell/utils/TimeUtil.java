package com.imooc.bootsell.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    public static Object dataTime(long date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(Long.parseLong(String.valueOf(date))));
    }


//    public static void main(String[] args) {
//        long longstr = System.currentTimeMillis();
//        System.out.println(dataTime(longstr));
//    }


}
