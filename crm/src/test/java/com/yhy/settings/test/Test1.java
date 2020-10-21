package com.yhy.settings.test;

import com.yhy.crm.utils.DateTimeUtil;
import com.yhy.crm.utils.MD5Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test1 {
    public static void main(String[] args) {
        //失效时间
        String expireTime = "2020-10-10 10:10:10";
        //系统当前时间
        String currentTime = DateTimeUtil.getSysTime();

        /*Date date =new Date();
        //System.out.println(date);
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str =sdf.format(date);
        System.out.println(str);系统当前时间*/

        //验证加密
        String pwd ="shuai15823872558";
        String pwd1 = MD5Util.getMD5(pwd);
        System.out.println(pwd1);
    }
}
