package com.yefeng.controller;

import com.yefeng.BgmService;
import com.yefeng.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "背景音乐业务的接口",tags = "背景音乐业务的controller")
@RestController
@RequestMapping("/bgm")
public class BgmController {

    private static Logger logger = LoggerFactory.getLogger(BgmController.class);

    @Autowired
    private BgmService bgmService;

    @ApiOperation(value = "获取背景音乐列表",notes = "背景音乐列表获取的接口")
    @GetMapping("/queryBgmList")
    public JsonResult queryBgmList() {
        return JsonResult.ok(bgmService.queryBgmList());
    }


}
