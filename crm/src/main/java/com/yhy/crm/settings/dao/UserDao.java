package com.yhy.crm.settings.dao;

import com.yhy.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    //数据库层接口
    User login(Map<String, String> map);

    List<User> getUserList();
}
