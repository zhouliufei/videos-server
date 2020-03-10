package com.yefeng;

import com.yefeng.dto.MineDTO;
import com.yefeng.dto.PageInputDTO;
import com.yefeng.pojo.User;
import com.yefeng.pojo.UserReport;
import com.yefeng.util.JsonResult;

import java.util.List;

public interface UserService {
    /**
     * 注册的接口
     */
    public void regist(User user);
    /**
     * 查询用户是否存在的接口
     */
    public boolean queryUserNameIsExist(String username);
    /**
     * 登录系统的接口
     */
    public JsonResult login(String username, String password);
    /**
     * 登出系统的接口
     */
    boolean logout(String userId) throws Exception;
    /**
     * 更新用户信息的接口
     */
    int updateUserInfo(User user);
    /**
     * 根据userId查询用户信息
     */
    MineDTO queryUserInfo(String userId,String fanId);
    /**
     * 关注用户的接口
     */
    boolean followAuthor(String userId, String authorId);
    /**
     * 取消用户关注的接口
     */
    boolean removeFollowAuthor(String userId, String authorId);
    /**
     * 举报用户的接口
     */
    void reportUser(UserReport userReport);
    /**
     * 获取用户列表的接口
     */
    List<User> queryUserList(PageInputDTO pageInputDTO);
}
