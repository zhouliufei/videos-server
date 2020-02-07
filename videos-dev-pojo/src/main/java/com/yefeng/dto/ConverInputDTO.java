package com.yefeng.dto;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConverInputDTO {
    @ApiParam(name = "视频主键ID",required = true)
    private String videoId;
    @ApiParam(name = "视频封面路径",required = true)
    private String coverPath;

}
