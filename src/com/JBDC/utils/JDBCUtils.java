package com.JBDC.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * JDBC的工具类
 * 设置为不可继承，并且配上私有的构造器，防止被new，它的方法都是静态方法
 * @author HANG
 */
public final class JDBCUtils {
    private static final String driverClass;
    private static final String url;
    private static final String username;
    private static final String password;

    private JDBCUtils() {}

    static {
        /* 加载属性文件并解析 */
        Properties props = new Properties();

        /* 如何获得属性文件的输入流？ */
        /* 通常情况下使用类的加载器的方式进行获取，为什么不用 FileInoutStream("src/jdbc.properties") ? */
        /* 因为将来的web项目需要发布到服务器上去，但是上面这种写法带有src路径，在服务器中没有src这种路径，所以我们通常不会采取这种方式 */
        InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");

        /* 通过load方法来解析属性文件，它的参数是一个能够代表属性文件的输入流 */
        try {
            props.load(is);
        } catch (IOException e) {
            System.out.println("加载属性文件失败!");
            e.printStackTrace();
        }

        driverClass = props.getProperty("driverClass");
        url = props.getProperty("url");
        username = props.getProperty("username");
        password = props.getProperty("password");
    }

    /**
     * 注册驱动的方法
     * @throws ClassNotFoundException
     */
    public static void loadDriver() throws ClassNotFoundException {
        Class.forName(driverClass);
    }

    /**
     * 获得连接的方法
     * 在这个静态方法中会注册驱动
     * @return 返回一个连接
     * @throws SQLException
     */
    public static Connection getConnection() throws Exception {
        loadDriver();
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    /**
     * 释放Statement，Connection。适用于增、删、改操作
     * @param stmt Statement
     * @param conn Connection
     */
    public static void release(Statement stmt, Connection conn) {
        if (stmt != null){
            try {
                stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            stmt = null;
        }
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            conn = null;
        }
    }

    /**
     * 释放ResultSet、Statement、Connection，适用于查操作
     * @param rs
     * @param stmt
     * @param conn
     */
    public static void release(ResultSet rs, Statement stmt, Connection conn) {
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            rs = null;
        }
        if (stmt != null){
            try {
                stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            stmt = null;
        }
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            conn = null;
        }
    }
}
