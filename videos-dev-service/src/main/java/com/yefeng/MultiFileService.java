package com.yefeng;

import com.yefeng.pojo.Video;
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
}
