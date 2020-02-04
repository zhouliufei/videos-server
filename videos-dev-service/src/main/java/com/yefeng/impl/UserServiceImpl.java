package com.yefeng.impl;

import com.yefeng.UserService;
import com.yefeng.dto.UserTokenDTO;
import com.yefeng.mapper.UserMapper;
import com.yefeng.pojo.User;
import com.yefeng.util.IDUtil;
import com.yefeng.util.JsonResult;
import com.yefeng.util.MD5Util;
import com.yefeng.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void regist(User user) {
        String id = IDUtil.getId();
        user.setId(id);
        userMapper.insertSelective(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryUserNameIsExist(String username) {
        return userMapper.queryIsExistByColumn("users","username",username) > 0;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public JsonResult login(String username, String password) {
        //1、通过用户名和密码获得用户
        User user = userMapper.getUser(username, password);
        if(user == null) {
            return JsonResult.errorTokenMsg("用户名或密码错误，请重试");
        }
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        //2、生成唯一的token
        String token = UUID.randomUUID().toString().replace("-", "");
        userTokenDTO.setToken(token);
        userTokenDTO.setUserId(user.getId());
        //3、存入token到redis中
        try {
            redisUtil.set(token, null, user.getId());
            redisUtil.set(user.getId(), null, token);
        } catch (Exception e) {
            return JsonResult.errorMessage(e.getMessage());
        }
        return JsonResult.ok(userTokenDTO);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean logout(String userId) throws Exception {
        String token = redisUtil.get(userId);
        redisUtil.delete(token);
        redisUtil.delete(userId);
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public int updateUserInfo(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User queryUserInfo(String userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        user.setPassword("");
        return user;
    }
}
