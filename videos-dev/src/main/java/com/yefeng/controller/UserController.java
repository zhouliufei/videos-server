package com.yefeng.controller;

import com.yefeng.MultiFileService;
import com.yefeng.UserService;
import com.yefeng.dto.FollowAuthorInputDTO;
import com.yefeng.dto.PageInputDTO;
import com.yefeng.pojo.User;
import com.yefeng.pojo.UserReport;
import com.yefeng.util.JsonResult;
import com.yefeng.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.spring.web.json.Json;

import java.io.FileNotFoundException;
import java.io.IOException;

@Api(value = "用户相关业务的接口",tags = "用户管理的controller")
@RestController
@RequestMapping("/user")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MultiFileService multiFileService;


    @ApiOperation(value = "用户头像上传",notes = "用户头像上传的接口")
    @PostMapping("/uploadFace")
    public JsonResult uploadFace(String userId,
                            @RequestParam("files") MultipartFile[] files) {
        if(StringUtil.isEmpty(userId)) {
            return JsonResult.errorMessage("获取用户信息出错，请重新登录");
        }
        if(files == null || files.length == 0) {
            return JsonResult.errorMessage("上传失败，未选择文件");
        }
        try {
            String uploadDb = multiFileService.uploadFace(userId,files);
            User user = new User();
            user.setId(userId);
            user.setFaceImage(uploadDb);
            if(userService.updateUserInfo(user) > 0) {
                return JsonResult.build("用户头像上传成功",200,uploadDb);
            } else {
                return JsonResult.errorMessage("用户信息修改失败");
            }
        } catch (FileNotFoundException e) {
            logger.error(e.toString(),e);
        } catch (IOException e) {
            logger.error(e.toString(),e);
        }
        return JsonResult.ok();
    }

    @GetMapping("/queryUserInfo")
    @ApiOperation(value = "用户基本信息获取",notes = "根据userId获取用户基本信息的接口")
    @ApiImplicitParam(name = "userId",value = "用户ID",required = true,
            dataType = "String",paramType = "query")
    public JsonResult queryUserInfo(@RequestParam("userId") String userId,
                                    @RequestParam("fanId") String fanId) {
        if(StringUtil.isEmpty(userId)) {
            return JsonResult.errorMessage("用户Id不能为空");
        }
        return JsonResult.ok(userService.queryUserInfo(userId,fanId));
    }

    @PostMapping("/followAuthor")
    @ApiOperation(value = "关注用户",notes = "关注作者的接口")
    public JsonResult followAuthor(@Validated @RequestBody FollowAuthorInputDTO followAuthorInputDTO) {
        try{
            userService.followAuthor(followAuthorInputDTO.getUserId(),followAuthorInputDTO.getAuthorId());
            return JsonResult.ok();
        }catch (Exception e) {
            return JsonResult.errorMessage(e.getMessage());
        }
    }

    @PostMapping("/removeFollowAuthor")
    @ApiOperation(value = "取消用户关注",notes = "取消用户关注的接口")
    public JsonResult removeFollowAuthor(@Validated @RequestBody FollowAuthorInputDTO followAuthorInputDTO) {
        try{
            userService.removeFollowAuthor(followAuthorInputDTO.getUserId(),followAuthorInputDTO.getAuthorId());
            return JsonResult.ok();
        }catch (Exception e) {
            return JsonResult.errorMessage(e.getMessage());
        }
    }

    @PostMapping("/reportUser")
    @ApiOperation(value = "举报用户",notes = "举报用户的接口")
    public JsonResult reportUser(@RequestBody UserReport userReport) {
        try{
            userService.reportUser(userReport);
            return JsonResult.ok("举报成功...平台因为您的举报更美好...");
        }catch (Exception e) {
            return JsonResult.errorMessage(e.getMessage());
        }
    }

    @PostMapping("/queryUserList")
    @ApiOperation(value = "获取用户列表",notes = "获取用户列表的接口")
    public JsonResult queryUserList(@RequestBody PageInputDTO pageInputDTO) {
        try{
            return JsonResult.ok( userService.queryUserList(pageInputDTO));
        }catch (Exception e) {
            return JsonResult.errorMessage(e.getMessage());
        }
    }
}
