package com.yefeng.impl;

import com.yefeng.BgmService;
import com.yefeng.MultiFileService;
import com.yefeng.dto.ConverInputDTO;
import com.yefeng.dto.UserTokenDTO;
import com.yefeng.mapper.UserMapper;
import com.yefeng.mapper.VideoMapper;
import com.yefeng.pojo.Bgm;
import com.yefeng.pojo.Video;
import com.yefeng.util.FFMpegUtil;
import com.yefeng.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
public class MultiFileServiceImpl implements MultiFileService {

    @Value("${fileUpload.filePath}")
    private String rootPath;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private BgmService bgmService;
    @Autowired
    private FFMpegUtil mpegUtil;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String uploadFace(String userId, MultipartFile[] files) throws IOException {
        String fileName = files[0].getOriginalFilename();
        //文件在数据库中保存的相对地址
        String uploadDB = "/" + userId + "/face/" + fileName;
        //文件最终保存的地址
        String dest = rootPath + uploadDB;
        File file = new File(dest);
        if(!file.getParentFile().exists() || !file.getParentFile().isDirectory()) {
            file.getParentFile().mkdirs();
        }
        files[0].transferTo(file);
        String faceUrl = userMapper.queryFaceUrl(userId);
        if(StringUtil.isEmpty(faceUrl)) {
            return uploadDB;
        }
        String deleteFilePath = rootPath + faceUrl;
        if(!StringUtil.isEmpty(deleteFilePath)) {
            File deleteFile = new File(deleteFilePath);
            if(deleteFile.exists()) {
                deleteFile.delete();
            }
        }
        return uploadDB;
    }

    @Override
    public String upload(Video video, MultipartFile file) throws IOException {
        //如果bgmId不为null,则合并视频和bgm并上传
        String fileName = file.getOriginalFilename();
        //文件在数据库中保存的相对地址
        String uploadDB = "/" + video.getUserId() + "/video/" + fileName;
        //文件最终保存的地址
        String dest = rootPath + uploadDB;
        File videoFile = new File(dest);
        if(!videoFile.getParentFile().exists() || !videoFile.getParentFile().isDirectory()) {
            videoFile.getParentFile().mkdirs();
        }
        //写入文件到磁盘
        file.transferTo(videoFile);
        //获取到了bgm地址与视频地址
        String outputPath = "";
        if(video.getAudioId() != null) {
            Bgm bgm = bgmService.queryBgmById(video.getAudioId());
            if(bgm != null) {
                String bgmPath = rootPath + bgm.getPath();
                outputPath = "/" + video.getUserId() + "/video/" + "convert_" + fileName;
                mpegUtil.convert(dest,
                        bgmPath,
                        Math.ceil(video.getVideoSeconds()),
                        rootPath + outputPath);

            }
        }
        //插入记录到数据库
        String videoId = UUID.randomUUID().toString();
        video.setId(videoId);
        if(!StringUtil.isEmpty(outputPath)) {
            video.setVideoPath(outputPath);
        }
        else {
            video.setVideoPath(uploadDB);
        }
        //上传时的播放状态，待管理员审核后是否能播放
        video.setStatus(2);
        video.setCreateTime(new Date());
        //设置被点赞的次数
        video.setLikeCounts(0L);
        videoMapper.insertSelective(video);
        return videoId;
    }

    @Override
    public void uploadCover(ConverInputDTO conver, UserTokenDTO userToken, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        //文件在数据库中保存的相对地址
        //TODO 需要改变的UserId
        String uploadDB = "/" + "094a3656-f21c-4963-a555-d61757ec4d06" + "/conver/" + fileName;
        //文件最终保存的地址
        String dest = rootPath + uploadDB;
        File destFile = new File(dest);
        if(!destFile.getParentFile().exists() || !destFile.getParentFile().isDirectory()) {
            destFile.getParentFile().mkdirs();
        }
        file.transferTo(destFile);
        String coverPath = videoMapper.queryCoverPath(conver.getVideoId());
        //更新数据库中的数据记录
        videoMapper.updateCoverPath(conver.getVideoId(),uploadDB);
        if(StringUtil.isEmpty(coverPath)) {
            return;
        }
        String deleteFilePath = rootPath + coverPath;
        if(!StringUtil.isEmpty(deleteFilePath)) {
            File deleteFile = new File(deleteFilePath);
            if(deleteFile.exists()) {
                deleteFile.delete();
            }
        }
    }
}
