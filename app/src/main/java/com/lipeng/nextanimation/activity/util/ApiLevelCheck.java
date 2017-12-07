package com.lipeng.nextanimation.activity.util;

import android.os.Build;

/**
 * Created by lipeng on 2017/12/7.
 * 检车API 版本
 */

public class ApiLevelCheck{

    /**
     * 检查当前版本的sdk版本是否为最小
     * @param api */
    public static boolean isAtLeast(int api){
        return Build.VERSION.SDK_INT >= api;
    }

    /**
     * 检查当前sdk版本是否比提供的api对应的版本低
     * @param api */
    public static boolean isLowerThan(int api){
        return Build.VERSION.SDK_INT < api;
    }
}
