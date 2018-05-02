package com.imooc.bootsell.utils;

import java.util.Random;

public class KeyUtil {
    /**
     * 生成唯一的订单号
     * 时间加上随机数
     * @return
     */

    public static synchronized String getUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(100000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);

    }
}
