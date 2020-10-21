package com.yhy.crm.settings.web.controller;

import com.yhy.crm.settings.domain.User;
import com.yhy.crm.settings.service.UserService;
import com.yhy.crm.settings.service.impl.UserServiceImpl;
import com.yhy.crm.utils.MD5Util;
import com.yhy.crm.utils.PrintJson;
import com.yhy.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//控制器
public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到用户控制器");

        String path = request.getServletPath();

        if ("/settings/user/login.do".equals(path)){
            login(request,response);
        }else if ("/settings/user/xxx.do".equals(path)){
            //xxx(request,response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入验证登录操作");

        //接受参数
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");

        //将密码的明文形式转换为MD5的密文形式
        loginPwd = MD5Util.getMD5(loginPwd);
        System.out.println("密文密码------------"+loginPwd);

        //接收浏览器的ip地址
        String ip =request.getRemoteAddr();
        System.out.println("ip--------:"+ip);

        //创建业务层对象,使用代理类形态的接口对象
        //UserService us =new UserServiceImpl();
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());



        try {
            User user =us.login(loginAct,loginPwd,ip);

            //把user对象保存到Session域中
            request.getSession().setAttribute("user",user);
            //如果程序执行到此处，说明业务层没有为controller（控制器）抛出任何异常，表示成功
            //{"success : true"}传递这种形式的数据
            PrintJson.printJsonFlag(response,true);
        }catch (Exception e){
            e.printStackTrace();
            //一旦程序执行了catch块的信息，说明业务层验证登录失败，为controller抛出了异常，表示失败
            //{"success" : true,"msg" : ?}需要多提供一个错误信息，错误信息由业务层提供
            String msg = e.getMessage();
            //使用map就行了，不用创建vo类
            Map<String,Object> map =new HashMap<String, Object>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);
        }
    }
}
