package com.yhy.crm.workbench.dao;

import com.yhy.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int save(Activity a);

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    int getTotaByCondition(Map<String, Object> map);
}
