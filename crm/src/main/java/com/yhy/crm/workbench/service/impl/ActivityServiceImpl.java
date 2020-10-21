package com.yhy.crm.workbench.service.impl;

import com.yhy.crm.utils.SqlSessionUtil;
import com.yhy.crm.vo.PaginationVO;
import com.yhy.crm.workbench.dao.ActivityDao;
import com.yhy.crm.workbench.domain.Activity;
import com.yhy.crm.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    //引入Dao层
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

    public boolean save(Activity a) {
        //打一个标记
        //最好是使用抛出自定义异常
        boolean flag =true;
        //受到影响的条数
        int count =activityDao.save(a);
        if (count!=1){
            flag = false;
        }

        return flag;
    }

    public PaginationVO<Activity> pageList(Map<String, Object> map) {

        //取得total
        int total = activityDao.getTotaByCondition(map);

        //取得dataList
        List<Activity> dataList = activityDao.getActivityListByCondition(map);

        //将total和dataList封装到VO中
        PaginationVO<Activity> vo =new PaginationVO<Activity>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        //返回VO
        return vo;
    }
}
