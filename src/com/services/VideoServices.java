package com.services;

import com.DAO.VideoDAO;
import com.JBDC.utils.JDBCUtils;
import com.bean.Video;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class VideoServices {
    /**
     * 分页查询全部留言
     *
     * @param page     当前页码
     * @param pageSize 每页记录数
     * @return
     */
    public static List<Video> getVideos(int page, int pageSize) {

        return VideoDAO.getVideos(page, pageSize);
    }

    /**
     * 分页查询某一个用户上传的视频
     *
     * @param page     当前页码
     * @param pageSize 每页记录数
     * @param username 查询的用户
     * @return
     */
    public static List<Video> getVideos(int page, int pageSize, String username) {

        return VideoDAO.getVideos(page, pageSize, username);
    }

    /**
     * 计算总共有多少个视频
     *
     * @return
     */
    public static int countVideo() {

        return VideoDAO.countVideo();
    }

    /**
     * 通过videoId获得该视频的储存路径
     *
     * @param videoId
     * @return
     */
    public static String getPath(int videoId) {

        return VideoDAO.getPath(videoId);
    }

    /**
     * 当视频被播放时，count+1
     *
     * @param videoId
     */
    public static void addCount(int videoId) {

        VideoDAO.addCount(videoId);
    }

    /**
     * 通过视频id返回上传者用户名
     *
     * @param videoId
     * @return
     */
    public static String queryUsername(int videoId) {

        return VideoDAO.queryUsername(videoId);
    }

    /**
     * 检查该视频id是否存在
     *
     * @param videoId
     * @return
     */
    public static boolean checkVideoId(int videoId) {

        return VideoDAO.checkVideoId(videoId);
    }

    /**
     * 根据视频id删除视频
     *
     * @param videoId
     */
    public static void deleteVideo(int videoId) {

        VideoDAO.deleteVideo(videoId);
    }

}
