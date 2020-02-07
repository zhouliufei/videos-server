package com.yefeng.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTokenDTO {

        public UserTokenDTO() {}

        public UserTokenDTO(String userId,String token) {
            this.userId = userId;
            this.token = token;
        }

//    @Getter
//    @Setter
    private String userId;
//    @Getter
//    @Setter
    private String token;

}
