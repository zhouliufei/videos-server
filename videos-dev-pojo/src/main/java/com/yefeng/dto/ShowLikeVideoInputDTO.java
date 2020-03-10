package com.yefeng.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class ShowLikeVideoInputDTO extends PageInputDTO {
    @NotNull
    private String userId;

}
