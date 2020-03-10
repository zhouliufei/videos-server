package com.yefeng.controller;

import com.yefeng.BgmService;
import com.yefeng.HomePageService;
import com.yefeng.dto.PageInputDTO;
import com.yefeng.dto.VideoPageInputDTO;
import com.yefeng.pojo.Video;
import com.yefeng.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "首页业务相关的接口",tags = "首页业务相关的controller")
@RestController
    @RequestMapping("/homePage")
public class HomePageController {

    private static Logger logger = LoggerFactory.getLogger(HomePageController.class);

    @Autowired
    private HomePageService homePageService;

    /**
     * @Description isSaveRecord = 1 需要保存
     * isSaveRecord = 0 不需要保存
     */
    @ApiOperation(value = "获取首页列表",notes = "获取首页列表的接口")
    @GetMapping("/queryHomePageList")
    public JsonResult queryHomePageList(@Validated VideoPageInputDTO pageInput) {
        return JsonResult.ok(homePageService.queryHomePageList(pageInput));
    }

    @ApiOperation(value = "获取热搜词汇",notes = "获取热搜词汇的接口")
    @GetMapping("/queryHot")
    public JsonResult queryHot() {
        return JsonResult.ok(homePageService.queryHot());
    }

}
