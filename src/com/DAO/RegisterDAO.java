package com.DAO;

import com.JBDC.utils.JDBCUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterDAO {
    /**
     * 实现注册功能，将用户名与密码写入数据库
     * @param username
     * @param password
     */
    public static void register(String username, String password){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            /* 1.获得连接 */
            conn = JDBCUtils.getConnection();

            /* 2.编写sql语句并预编译 */
            String sql = "INSERT INTO user VALUE(NULL, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            /* 3.设置参数*/
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            /* 4.执行sql语句 */
            pstmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            /* 5.释放资源 */
            JDBCUtils.release(pstmt, conn);
        }
    }

    /**
     * 检查用户名是否已经被注册
     * @param username
     * @return
     */
    public static boolean CheckUsername(String username){
        boolean flag = false;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = JDBCUtils.getConnection();

            String sql = "SELECT username FROM user WHERE username=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            rs = pstmt.executeQuery();

            /* 如果检索出了用户名，说明该用户名已经被注册 */
            if (rs.next()) flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.release(rs, pstmt, conn);
        }

        return flag;
    }

    /**
     * 对用户输入的密码进行加密再储存进数据库
     * 这是从网上copy来的md5加密
     * @param password
     */
    public static String Encryption(String password){
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);// // 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
}
