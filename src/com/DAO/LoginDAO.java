package com.DAO;

import com.JBDC.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDAO {
    /**
     * 校验登录
     * @param username
     * @param password
     * @return
     */
    public static boolean check(String username, String password){
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pstmt = null;             //使用PreparedStatement防止sql注入
        ResultSet rs = null;

        try{
            /* 1.创建连接 */
            conn = JDBCUtils.getConnection();

            /* 2.编写sql语句并对其进行预编译 */
            String sql = "SELECT * FROM user WHERE username=? AND password=?";
            pstmt = conn.prepareStatement(sql);

            /* 3.设置参数*/
            /* 将用户登录输入的密码转换为md5加密的形式与数据库中已经加密的密码进行比较 */
            password = RegisterDAO.Encryption(password);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            /* 3.执行sql语句，如果查询到用户名和密码则将flag变为true */
            rs = pstmt.executeQuery();
            if (rs.next()) flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            /* 4.释放资源 */
            JDBCUtils.release(rs, pstmt, conn);
        }

        return flag;
    }
}
