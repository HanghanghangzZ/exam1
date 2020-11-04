package com.bean;

/**
 * Video Bean
 */
public class Video {
    private int videoId;
    private int userId;
    private String videoName;
    private String path;
    private int count;
    private String username;

    public Video() {
    }

    public Video(int videoId, int userId, String username, String videoName, String path, int count) {
        this.videoId = videoId;
        this.userId = userId;
        this.username = username;
        this.videoName = videoName;
        this.path = path;
        this.count = count;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
