package com.yefeng.dto;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Setter
@Getter
public class PageInputDTO {
    //当前页
    @ApiParam(name = "当前页",required = true)
    @Min(1)
    private int page;
    //分页大小
    @ApiParam(name = "分页大小",required = true)
    @Min(1)
    private int pageSize;

}
