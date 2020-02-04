package com.yefeng.controller;

import com.yefeng.MultiFileService;
import com.yefeng.UserService;
import com.yefeng.pojo.User;
import com.yefeng.util.JsonResult;
import com.yefeng.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public JsonResult queryUserInfo(@RequestParam("userId") String userId) {
        if(StringUtil.isEmpty(userId)) {
            return JsonResult.errorMessage("用户Id不能为空");
        }
        User user = new User();
        return JsonResult.ok(userService.queryUserInfo(userId));
    }


}
