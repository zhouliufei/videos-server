package com.yefeng.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class FFMpegUtil {
    private static final Logger logger = LoggerFactory.getLogger(FFMpegUtil.class);
    //ffmpeg.exe的位置
    @Value("${ffmpegPath}")
    private String ffmpegPath;

    public FFMpegUtil() {}

    public FFMpegUtil(String path) {
        this.ffmpegPath = path;
    }

    public boolean convert(String videoPath,String bgmPath,double seconds,String videoOutputPath) throws IOException {
        //得到消音后的视频路径
        String eTVideoPath = eliminateTrack(videoPath);
        if(eTVideoPath == null) {
            eTVideoPath = videoPath;
        }
       //ffmpeg.exe -i video1.mp4 -i bgm.mp3 -t 7 -y 新的视频.mp4
        List<String> command = new ArrayList<>();
        command.add(ffmpegPath);

        command.add("-i");
        command.add(eTVideoPath);

        command.add("-i");
        command.add(bgmPath);

        command.add("-t");
        command.add(String.valueOf(seconds));

        command.add("-y");
        command.add(videoOutputPath);

        for(String c:command) {
            System.out.print(c + " ");
        }

        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();
        try {
            releaseStream(process);
            File file = new File(eTVideoPath);
            if(file.exists()) {
                file.delete();
            } else {
                file = null;
            }
        }catch (Exception e) {
            logger.error("ffmepg音频合并后资源释放报错" + e.getMessage());
        }
        return true;
    }

    /**
     * 消除原视频的音轨
     * @return
     */
    private String eliminateTrack(String videoPath) throws IOException {
        //ffmpeg.exe -i 1.mp4 -c:v copy -an xiaoy_1.mp4
        System.out.println(videoPath);
        String[] result = videoPath.split("video/");
        if(result == null || result.length == 0) {
            return null;
        }
        String resultName = result[0] + "video/eliminateTrack_" + result[1];
        List<String> command = new ArrayList<>();
        command.add(ffmpegPath);

        command.add("-i");
        command.add(videoPath);

        command.add("-c:v");
        command.add("copy");

        command.add("-an");
        command.add(resultName);

        for(String c:command) {
            System.out.print(c + " ");
        }
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();
        try {
           releaseStream(process);
        }catch (Exception e) {
            logger.error("ffmepg音频消音后资源释放报错" + e.getMessage());
        }
        return resultName;
    }

    private void releaseStream(Process process) throws IOException {
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);

        String line = "";
        while ((line = br.readLine()) != null) {
        }
        if(br!= null) {
            br.close();
        }
        if(inputStreamReader != null) {
            inputStreamReader.close();
        }
        if(errorStream != null) {
            errorStream.close();
        }
    }
}
