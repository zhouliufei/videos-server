package com.yefeng;

import com.yefeng.pojo.User;
import com.yefeng.util.JsonResult;

public interface UserService {

    public void regist(User user);

    public boolean queryUserNameIsExist(String username);

    public JsonResult login(String username, String password);
}
