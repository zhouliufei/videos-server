package com.yefeng.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "视频基础信息对象",description = "这是视频基础信息对象")
public class Video {
    private String id;
    @ApiModelProperty(value = "用户ID",name = "userId",required = true)
    private String userId;
    @ApiModelProperty(value = "背景音乐Id",name = "audioId")
    private String audioId;
    @ApiModelProperty(value = "视频描述",name = "videoDesc",required = true)
    private String videoDesc;

    private String videoPath;
    @ApiModelProperty(value = "视频时长",name = "videoSeconds",required = true)
    private Float videoSeconds;
    @ApiModelProperty(value = "视频宽度",name = "videoWidth",required = true)
    private Integer videoWidth;
    @ApiModelProperty(value = "视频长度",name = "videoHeight",required = true)
    private Integer videoHeight;

    private String coverPath;

    private Long likeCounts;

    private Integer status;

    private Date createTime;

    public Video(String id, String userId, String audioId, String videoDesc, String videoPath, Float videoSeconds, Integer videoWidth, Integer videoHeight, String coverPath, Long likeCounts, Integer status, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.audioId = audioId;
        this.videoDesc = videoDesc;
        this.videoPath = videoPath;
        this.videoSeconds = videoSeconds;
        this.videoWidth = videoWidth;
        this.videoHeight = videoHeight;
        this.coverPath = coverPath;
        this.likeCounts = likeCounts;
        this.status = status;
        this.createTime = createTime;
    }

    public Video() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getAudioId() {
        return audioId;
    }

    public void setAudioId(String audioId) {
        this.audioId = audioId == null ? null : audioId.trim();
    }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc == null ? null : videoDesc.trim();
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath == null ? null : videoPath.trim();
    }

    public Float getVideoSeconds() {
        return videoSeconds;
    }

    public void setVideoSeconds(Float videoSeconds) {
        this.videoSeconds = videoSeconds;
    }

    public Integer getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(Integer videoWidth) {
        this.videoWidth = videoWidth;
    }

    public Integer getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(Integer videoHeight) {
        this.videoHeight = videoHeight;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath == null ? null : coverPath.trim();
    }

    public Long getLikeCounts() {
        return likeCounts;
    }

    public void setLikeCounts(Long likeCounts) {
        this.likeCounts = likeCounts;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
