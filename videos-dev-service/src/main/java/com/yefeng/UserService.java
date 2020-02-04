package com.yefeng;

import com.yefeng.pojo.User;
import com.yefeng.util.JsonResult;

public interface UserService {

    public void regist(User user);

    public boolean queryUserNameIsExist(String username);

    public JsonResult login(String username, String password);

    boolean logout(String userId) throws Exception;

    int updateUserInfo(User user);

    User queryUserInfo(String userId);
}
