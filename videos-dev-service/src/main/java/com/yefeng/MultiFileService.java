package com.yefeng;

import com.yefeng.dto.ConverInputDTO;
import com.yefeng.dto.UserTokenDTO;
import com.yefeng.pojo.Video;
import com.yefeng.util.JsonResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MultiFileService {
    /**
     *  用户头像存储
     * @param userId
     * @param files
     */
    String uploadFace(String userId, MultipartFile[] files) throws IOException;

    String upload(Video video, MultipartFile file) throws IOException;

    void uploadCover(ConverInputDTO conver, UserTokenDTO userToken, MultipartFile file) throws IOException;
}
