package com.yhy.crm.workbench.web.controller;

import com.yhy.crm.settings.domain.User;
import com.yhy.crm.settings.service.UserService;
import com.yhy.crm.settings.service.impl.UserServiceImpl;
import com.yhy.crm.utils.DateTimeUtil;
import com.yhy.crm.utils.PrintJson;
import com.yhy.crm.utils.ServiceFactory;
import com.yhy.crm.utils.UUIDUtil;
import com.yhy.crm.vo.PaginationVO;
import com.yhy.crm.workbench.domain.Activity;
import com.yhy.crm.workbench.service.ActivityService;
import com.yhy.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//控制器
public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到市场活动用户控制器");

        String path = request.getServletPath();

        if ("/workbench/activity/getUserList.do".equals(path)){
            getUserList(request,response);
        }else if ("/workbench/activity/save.do".equals(path)){
            save(request,response);
        }else if ("/workbench/activity/pageList.do".equals(path)){
            pageList(request,response);
        }



    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到查询市场活动信息列表的操作（结合条件查询+分页查询）");

        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        //为了计算出略过的记录数而取的工具值
        String pageNoStr = request.getParameter("pageNo");
        int pageNO =Integer.valueOf(pageNoStr);
        //每页展现的记录数
        String pageSizeStr = request.getParameter("pageSize");
        int pageSize = Integer.valueOf(pageSizeStr);
        //计算出略过的记录数
        int skipCount = (pageNO-1)*pageSize;

        //打包成map集合
        Map<String,Object> map =new HashMap<String, Object>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        PaginationVO<Activity> vo=as.pageList(map);

        //vo转换成我们需要的{"total" : 100,"dataList" : [{市场活动1},{2},{3}]}
        PrintJson.printJsonObj(response,vo);


    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动添加操作");

       String id = UUIDUtil.getUUID();
       String owner = request.getParameter("owner");
       String name = request.getParameter("name");
       String startDate = request.getParameter("startDate");
       String endDate = request.getParameter("endDate");
       String  cost = request.getParameter("cost");
       String description = request.getParameter("description");
       //创建时间，当前系统时间
       String createTime = DateTimeUtil.getSysTime();
       //创建人，当前登录用户
       String createBy = ((User)request.getSession().getAttribute("user")).getName();

       //把上面的值写成一个对象
        Activity a =new Activity();
        a.setId(id);
        a.setCost(cost);
        a.setStartDate(startDate);
        a.setOwner(owner);
        a.setName(name);
        a.setEndDate(endDate);
        a.setDescription(description);
        a.setCreateTime(createTime);
        a.setCreateBy(createBy);


        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.save(a);

        PrintJson.printJsonFlag(response,flag);



    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得用户信息列表");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();
        //调用工具，解析成 Json数组
        PrintJson.printJsonObj(response,uList);
    }
}
