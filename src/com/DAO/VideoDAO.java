package com.DAO;

import com.JBDC.utils.JDBCUtils;
import com.bean.Video;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 视频DAO
 */
public class VideoDAO {

    /**
     * 分页查询全部留言
     *
     * @param page     当前页码
     * @param pageSize 每页记录数
     * @return
     */
    public static List<Video> getVideos(int page, int pageSize) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Video> videos = new ArrayList<>();

        try {
            conn = JDBCUtils.getConnection();

            String sql = "SELECT * FROM video ORDER BY count DESC limit ?,?";
            pstmt = conn.prepareStatement(sql);

            /* 按照不同的页码取出不同段的记录 */
            pstmt.setInt(1, (page - 1) * pageSize);
            pstmt.setInt(2, pageSize);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                videos.add(new Video(rs.getInt("videoId"),
                        rs.getInt("userId"),
                        rs.getString("username"),
                        rs.getString("videoName"),
                        rs.getString("path"),
                        rs.getInt("count")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, pstmt, conn);
        }

        return videos;
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
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Video> videos = new ArrayList<>();

        try {
            conn = JDBCUtils.getConnection();


            String sql = "SELECT * FROM video WHERE username=? ORDER BY count DESC limit ?,?";
            pstmt = conn.prepareStatement(sql);

            /* 按照不同的页码取出不同段的记录 */
            pstmt.setString(1, username);
            pstmt.setInt(2, (page - 1) * pageSize);
            pstmt.setInt(3, pageSize);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                videos.add(new Video(rs.getInt("videoId"),
                        rs.getInt("userId"),
                        rs.getString("username"),
                        rs.getString("videoName"),
                        rs.getString("path"),
                        rs.getInt("count")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, pstmt, conn);
        }

        return videos;
    }

    /**
     * 计算总共有多少个视频
     *
     * @return
     */
    public static int countVideo() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtils.getConnection();

            String sql = "SELECT COUNT(*) total FROM video";
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, pstmt, conn);
        }

        return 0;
    }

    /**
     * 根据视频id获取视频路径
     *
     * @param videoId
     * @return
     */
    public static String getPath(int videoId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String path = "";

        try {
            conn = JDBCUtils.getConnection();

            String sql = "SELECT path FROM video WHERE videoId=?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, videoId);

            rs = pstmt.executeQuery();
            while (rs.next()) path = rs.getString("path");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, pstmt, conn);
        }
        return path;
    }

    /**
     * 当视频被播放时，count+1
     *
     * @param videoId
     */
    public static void addCount(int videoId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = JDBCUtils.getConnection();

            String sql = "UPDATE video SET count=count+1 WHERE videoId=?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, videoId);

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(pstmt, conn);
        }
    }

    /**
     * 通过视频id返回上传者用户名
     *
     * @param videoId
     * @return
     */
    public static String queryUsername(int videoId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String username = "";

        try {
            conn = JDBCUtils.getConnection();

            String sql = "SELECT username FROM video WHERE videoId=?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, videoId);

            rs = pstmt.executeQuery();

            while (rs.next()) username = rs.getString("username");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, pstmt, conn);
        }
        return username;
    }

    /**
     * 检查该视频id是否存在
     *
     * @param videoId
     * @return
     */
    public static boolean checkVideoId(int videoId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean flag = false;

        try {
            conn = JDBCUtils.getConnection();

            String sql = "SELECT videoId FROM video WHERE videoId=?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, videoId);

            rs = pstmt.executeQuery();

            while (rs.next()) flag = true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, pstmt, conn);
        }
        return flag;
    }

    /**
     * 根据视频id删除视频
     *
     * @param videoId
     */
    public static void deleteVideo(int videoId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = JDBCUtils.getConnection();

            String sql = "DELETE FROM video WHERE videoId=?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, videoId);

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(pstmt, conn);
        }
    }
}
