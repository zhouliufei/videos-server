package com.yefeng.controller;

import com.yefeng.UserService;
import com.yefeng.dto.UserTokenDTO;
import com.yefeng.util.JsonResult;
import com.yefeng.util.StringUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yefeng.pojo.User;

@Api(value = "用户注册和登录的接口",tags = "注册和登录的controller")
@RestController
public class RegistLoginController {

    private static Logger logger = LoggerFactory.getLogger(RegistLoginController.class);
    @Autowired
    private UserService userService;
    /**
     * 用户注册
     * @param user
     * @return
     */
    @ApiOperation(value = "用户注册",notes = "通过用户名和密码进行注册")
    @RequestMapping(value = "/regist/regist",method = RequestMethod.POST)
    public JsonResult regist(@RequestBody User user) throws Exception {
        //1、判断用户名和密码是否存在
        if(StringUtil.isEmpty(user.getUsername()) || StringUtil.isEmpty(user.getPassword())) {
            return JsonResult.errorMessage("注册用户的用户名和密码不能为空");
        }
        //2、判断用户是否已经注册
        if(userService.queryUserNameIsExist(user.getUsername())) {
            return JsonResult.errorMessage("用户名已经存在，请换一个用户名再注册");
        }
        //3、注册
        user.setNickname(user.getUsername());
        user.setFansCounts(0);
        user.setFollowCounts(0);
        user.setReceiveLikeCounts(0);
        userService.regist(user);
        return JsonResult.ok();
    }

    /**
     * 用户登录
     */
    @ApiOperation(value = "用户登录",notes = "通过用户名和密码进行登录")
    @RequestMapping(value = "/login/login",method=RequestMethod.POST)
    public JsonResult login(@RequestBody User user) {
        //1、判断用户用户名或密码是否为空
        if(StringUtil.isEmpty(user.getUsername()) || StringUtil.isEmpty(user.getPassword())) {
            return JsonResult.errorMessage("用户名或密码未输入");
        }
        //2、执行登录流程
        return userService.login(user.getUsername(),user.getPassword());
    }

    @ApiOperation(value = "用户注销",notes = "用户注销的接口")
    @ApiImplicitParam(name = "userId",value = "用户ID",required = true,
            dataType = "String",paramType = "query")
    @PostMapping(value = "/logout")
    public JsonResult logout(@RequestBody UserTokenDTO userToken) {
        try {
            if(userService.logout(userToken.getUserId())) {
                return JsonResult.sentMessage("注销成功");
            }
        } catch (Exception e) {
            return JsonResult.errorMessage(e.getMessage());
        }
        return JsonResult.sentMessage("注销成功");
    }


}
