package com.DAO;

import com.JBDC.utils.JDBCUtils;
import com.bean.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDAO {

    /**
     * 添加关注与被关注关系
     *
     * @param noticeUsername   关注着用户名
     * @param byNoticeUsername 被关注着用户名
     */
    public static void notice(String noticeUsername, String byNoticeUsername) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = JDBCUtils.getConnection();

            String sql = "INSERT INTO noticer VALUE(?,?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, getUserId(noticeUsername));
            pstmt.setInt(2, getUserId(byNoticeUsername));

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(pstmt, conn);
        }
    }

    /**
     * 检查是否已经存在关注与被关注关系
     *
     * @param noticeUsername
     * @param byNoticeUsername
     * @return 存在返回true，不存在返回false
     */
    public static boolean isNotice(String noticeUsername, String byNoticeUsername) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean flag = false;

        try {
            conn = JDBCUtils.getConnection();

            String sql = "SELECT * FROM noticer WHERE noticerId=? AND byNoticerId=?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, getUserId(noticeUsername));
            pstmt.setInt(2, getUserId(byNoticeUsername));

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
     * 通过唯一的用户名获得用户id
     *
     * @param username
     * @return
     */
    public static int getUserId(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int id = 0;

        try {
            conn = JDBCUtils.getConnection();

            String sql = "SELECT id FROM user WHERE username=?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, username);

            rs = pstmt.executeQuery();

            if (rs.next()) id = rs.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, pstmt, conn);
        }
        return id;
    }

    /**
     * 通过唯一的用户id获得用户名
     *
     * @param id 用户id
     * @return
     */
    public static String getUsername(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String username = "";

        try {
            conn = JDBCUtils.getConnection();

            String sql = "SELECT username FROM user WHERE id=?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            rs = pstmt.executeQuery();

            if (rs.next()) username = rs.getString("username");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, pstmt, conn);
        }
        return username;
    }

    /**
     * 获得关注的用户的名单
     *
     * @param page
     * @param pageSize
     * @param noticerId
     * @return
     */
    public static List<User> getNoticerUsers(int page, int pageSize, int noticerId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            conn = JDBCUtils.getConnection();

            String sql = "SELECT * FROM noticer WHERE noticerId=? limit ?,?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, noticerId);
            pstmt.setInt(2, (page - 1) * pageSize);
            pstmt.setInt(3, pageSize);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                int byNoticerId = rs.getInt("byNoticerId");
                users.add(new User(byNoticerId,
                        getUsername(byNoticerId)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, pstmt, conn);
        }

        return users;
    }

    /**
     * 获得粉丝名单
     *
     * @param page
     * @param pageSize
     * @param byNoticerId
     * @return
     */
    public static List<User> getByNoticerUsers(int page, int pageSize, int byNoticerId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            conn = JDBCUtils.getConnection();

            String sql = "SELECT noticerId FROM noticer WHERE byNoticerId=? limit ?,?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, byNoticerId);
            pstmt.setInt(2, (page - 1) * pageSize);
            pstmt.setInt(3, pageSize);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                int noticerId = rs.getInt("noticerId");
                users.add(new User(noticerId,
                        getUsername(noticerId)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, pstmt, conn);
        }

        return users;
    }

    /**
     * 统计关注的用户的数量
     *
     * @param noticerId
     * @return
     */
    public static int countNoticer(int noticerId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtils.getConnection();

            String sql = "SELECT COUNT(*) total FROM noticer WHERE noticerId=?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, noticerId);

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
     * 统计粉丝数量
     *
     * @param byNoticerId
     * @return
     */
    public static int countByNoticer(int byNoticerId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtils.getConnection();

            String sql = "SELECT COUNT(*) total FROM noticer WHERE byNoticerId=?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, byNoticerId);

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
     * 添加历史浏览记录
     *
     * @param visitorId
     * @param hostId
     */
    public static void addHistory(int visitorId, int hostId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        try {
            conn = JDBCUtils.getConnection();

            String sql = "INSERT INTO history(visitorId,visitorUsername,hostId,visitTime) VALUES(?,?,?,?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, visitorId);
            pstmt.setString(2, getUsername(visitorId));
            pstmt.setInt(3, hostId);
            pstmt.setTimestamp(4, currentTime);

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(pstmt, conn);
        }
    }

    /**
     * 获得浏览该主页的记录
     *
     * @param page
     * @param pageSize
     * @param hostId
     * @return
     */
    public static List<User> getHistory(int page, int pageSize, int hostId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            conn = JDBCUtils.getConnection();

            String sql = "SELECT * FROM history WHERE hostId=? ORDER BY visitTime DESC limit ?,? ";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, hostId);
            pstmt.setInt(2, (page - 1) * pageSize);
            pstmt.setInt(3, pageSize);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                users.add(new User(rs.getInt("visitorId"),
                        rs.getString("visitorUsername"),
                        rs.getDate("visitTime")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, pstmt, conn);
        }
        return users;
    }

    /**
     * 统计访问该主页的人数
     *
     * @param hostId
     * @return
     */
    public static int countHistory(int hostId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtils.getConnection();

            String sql = "SELECT COUNT(*) total FROM history WHERE hostId=?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, hostId);

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
}
