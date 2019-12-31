package com.yefeng.controller;

import com.yefeng.UserService;
import com.yefeng.annotation.LoginUser;
import com.yefeng.dto.UserTokenDTO;
import com.yefeng.util.JsonResult;
import com.yefeng.util.MD5Util;
import com.yefeng.util.StringUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yefeng.pojo.User;

@Api(value = "用户注册和登录的接口",tags = "注册和登录的controller")
@RestController
public class UserController {

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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",paramType = "query",value = "用户名",required = true),
            @ApiImplicitParam(name = "password",paramType = "query",value = "密码",required = true)
    })
    @RequestMapping(value = "/login/login",method=RequestMethod.POST)
    public JsonResult login(@RequestParam("username") String username, @RequestParam("password")String password) {
        //1、判断用户用户名或密码是否为空
        if(StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
            return JsonResult.errorMessage("用户名或密码未输入");
        }
        //2、执行登录流程
        return userService.login(username,password);
    }

    @ApiOperation(value = "用户测试",notes = "通过token进行测试")
    @RequestMapping(value = "/hello",method = RequestMethod.POST)
    public JsonResult hello(String token,@LoginUser UserTokenDTO userTokenDTO) throws Exception {
        System.out.println(userTokenDTO.getToken());
        System.out.println(userTokenDTO.getUserId());
        return JsonResult.ok();
    }

}
