package com.yefeng.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class FollowAuthorInputDTO {
    @NotNull
    private String userId;
    @NotNull
    private String authorId;
}
