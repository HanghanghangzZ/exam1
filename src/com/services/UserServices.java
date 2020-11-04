package com.services;

import com.DAO.UserDAO;
import com.JBDC.utils.JDBCUtils;
import com.bean.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class UserServices {
    /**
     * 添加关注与被关注关系
     *
     * @param noticeUsername   关注着用户名
     * @param byNoticeUsername 被关注着用户名
     */
    public static void notice(String noticeUsername, String byNoticeUsername) {

        UserDAO.notice(noticeUsername, byNoticeUsername);
    }

    /**
     * 检查是否已经存在关注与被关注关系
     *
     * @param noticeUsername
     * @param byNoticeUsername
     * @return 存在返回true，不存在返回false
     */
    public static boolean isNotice(String noticeUsername, String byNoticeUsername) {

        return UserDAO.isNotice(noticeUsername, byNoticeUsername);
    }

    /**
     * 通过唯一的用户名获得用户id
     *
     * @param username
     * @return
     */
    public static int getUserId(String username) {

        return UserDAO.getUserId(username);
    }

    /**
     * 通过唯一的用户id获得用户名
     *
     * @param id 用户id
     * @return
     */
    public static String getUsername(int id) {

        return UserDAO.getUsername(id);
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

        return UserDAO.getNoticerUsers(page, pageSize, noticerId);
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

        return UserDAO.getByNoticerUsers(page, pageSize, byNoticerId);
    }

    /**
     * 统计关注的用户的数量
     *
     * @param noticerId
     * @return
     */
    public static int countNoticer(int noticerId) {

        return UserDAO.countNoticer(noticerId);
    }

    /**
     * 统计粉丝数量
     *
     * @param byNoticerId
     * @return
     */
    public static int countByNoticer(int byNoticerId) {

        return UserDAO.countByNoticer(byNoticerId);
    }

    /**
     * 添加历史浏览记录
     *
     * @param visitorId
     * @param hostId
     */
    public static void addHistory(int visitorId, int hostId) {

        UserDAO.addHistory(visitorId, hostId);
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

        return UserDAO.getHistory(page, pageSize, hostId);
    }

    /**
     * 统计访问该主页的人数
     *
     * @param hostId
     * @return
     */
    public static int countHistory(int hostId) {

        return UserDAO.countHistory(hostId);
    }
}
