package com.yefeng.dto;

import com.yefeng.pojo.User;

public class MineDTO extends User {

    private boolean isFollow;

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }
}
