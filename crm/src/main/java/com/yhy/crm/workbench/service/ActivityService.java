package com.yhy.crm.workbench.service;

import com.yhy.crm.vo.PaginationVO;
import com.yhy.crm.workbench.domain.Activity;

import java.util.Map;

public interface ActivityService {
    boolean save(Activity a);

    PaginationVO<Activity> pageList(Map<String, Object> map);
    /*业务层接口*/
}
