package com.yefeng.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PageResult {

    private int page; //当前页
    private int total; //总页数
    private long records; //总记录数
    private List<?> rows; //每行显示的内容

}
