package com.yefeng;

import com.yefeng.dto.UserTokenDTO;

public class UserLoginThreadLocal {

    private static final ThreadLocal<UserTokenDTO> theadLocal = new ThreadLocal<>();

    private UserLoginThreadLocal() {}

    public static void set(UserTokenDTO userTokenDTO) {
        theadLocal.set(userTokenDTO);
    }

    public static UserTokenDTO get() {
        return theadLocal.get();
    }

    public static void remove() {
        theadLocal.remove();
    }

}
