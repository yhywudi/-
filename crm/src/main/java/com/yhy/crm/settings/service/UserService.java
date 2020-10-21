package com.yhy.crm.settings.service;

import com.yhy.crm.exception.LoginException;
import com.yhy.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList();
}
