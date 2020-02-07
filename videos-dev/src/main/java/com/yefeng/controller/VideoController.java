package com.yefeng.controller;

import com.yefeng.BgmService;
import com.yefeng.MultiFileService;
import com.yefeng.annotation.LoginUser;
import com.yefeng.dto.ConverInputDTO;
import com.yefeng.dto.UserTokenDTO;
import com.yefeng.pojo.Bgm;
import com.yefeng.pojo.User;
import com.yefeng.pojo.Video;
import com.yefeng.util.JsonResult;
import com.yefeng.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@Api(value = "视频相关业务的接口",tags = "视频业务的controller")
@RestController
@RequestMapping("/video")
public class VideoController {

    private static Logger logger = LoggerFactory.getLogger(VideoController.class);

    @Autowired
    private MultiFileService multiFileService;

    @ApiOperation(value = "上传视频",notes = "上传视频的接口")
    @PostMapping("/upload")
    public JsonResult upload(Video video,
                             @RequestParam("file") MultipartFile file) {
        if(StringUtil.isEmpty(video.getUserId())) {
            return JsonResult.errorMessage("获取用户信息出错，请重新登录");
        }
        if(file == null) {
            return JsonResult.errorMessage("上传失败，未选择文件");
        }
        String videoId = "";
        try {
            videoId = multiFileService.upload(video,file);
        } catch (FileNotFoundException e) {
            return JsonResult.errorMessage("短视频获取失败");
        } catch (IOException e) {
            return JsonResult.errorMessage("短视频上传处理");
        }
        return JsonResult.ok(videoId);
    }

    @ApiOperation(value = "上传封面",notes = "上传封面的接口")
    @PostMapping("/uploadCover")
    public JsonResult uploadCover(ConverInputDTO conver,
                                  @LoginUser UserTokenDTO userToken,
                                  @RequestParam("file") MultipartFile file) {
        if(StringUtil.isEmpty(conver.getVideoId())) {
            return JsonResult.errorMessage("未传入视频主键ID");
        }
        if(file == null) {
            return JsonResult.errorMessage("上传失败，未选择文件");
        }
        try {
            multiFileService.uploadCover(conver,userToken,file);
            return JsonResult.ok();
        } catch (IOException e) {
            return JsonResult.errorMessage("封面上传失败");
        }
    }



}
