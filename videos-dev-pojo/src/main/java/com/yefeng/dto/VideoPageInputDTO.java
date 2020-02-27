package com.yefeng.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VideoPageInputDTO extends PageInputDTO {

    private Integer isSaveRecord;
    private String desc;

}

