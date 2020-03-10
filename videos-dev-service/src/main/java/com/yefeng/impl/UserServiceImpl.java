package com.yefeng.impl;

import com.yefeng.UserService;
import com.yefeng.dto.MineDTO;
import com.yefeng.dto.PageInputDTO;
import com.yefeng.dto.UserTokenDTO;
import com.yefeng.mapper.UserFanMapper;
import com.yefeng.mapper.UserMapper;
import com.yefeng.mapper.UserReportMapper;
import com.yefeng.pojo.User;
import com.yefeng.pojo.UserFan;
import com.yefeng.pojo.UserReport;
import com.yefeng.util.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserFanMapper userFanMapper;
    @Autowired
    private UserReportMapper userReportMapper;

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
    public MineDTO queryUserInfo(String userId,String fanId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null) {
            return null;
        }
        user.setPassword("");
        MineDTO mineDTO = new MineDTO();
        BeanUtils.copyProperties(user,mineDTO);
        List<UserFan> list = userFanMapper.selectByUIdFId(userId,fanId);
        if(list!= null && !list.isEmpty()) {
            mineDTO.setFollow(true);
        }
        return mineDTO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean followAuthor(String userId, String authorId) {
        String id = UUID.randomUUID().toString();
        UserFan userFan = new UserFan();
        userFan.setId(id);
        userFan.setFanId(userId);
        userFan.setUserId(authorId);
        try{
            //1、插入用户粉丝关系表
            userFanMapper.insert(userFan);
            //2、增加作者粉丝数量
            userMapper.addFansCount(authorId);
            //3、增加用户自己的关注数量
            userMapper.addFollowCount(userId);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean removeFollowAuthor(String userId, String authorId) {
        try {
            //1、删除用户粉丝关系表中的数据
            userFanMapper.deleteByUserIdAndAuthorId(authorId,userId);
            //2、减少当前视频作者的粉丝数量
            userMapper.reduceFansCount(authorId);
            //3、减少用户关注的数量
            userMapper.reduceFollowCount(userId);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void reportUser(UserReport userReport) {
        String id = UUID.randomUUID().toString();
        userReport.setId(id);
        userReport.setCreateDate(new Date());
        userReportMapper.insert(userReport);
    }

    /**
     * 获取用户需要的页数和分页大小，若参数不合法
     * 当前页设置成第一页
     * 分页大小设置成系统默认分页大小
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> queryUserList(PageInputDTO pageInputDTO) {
        if(pageInputDTO.getPage() <= 0 || pageInputDTO.getPageSize() <= 0) {
            pageInputDTO.setPage(IntegerCommon.DEFALUT_PAGE);
            pageInputDTO.setPageSize(IntegerCommon.DEFALUT_PAGESIZE);
        }
        return userMapper.queryUserList(pageInputDTO.getPage(),
                pageInputDTO.getPageSize());
    }
}
