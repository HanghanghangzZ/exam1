package com.DAO;

import com.JBDC.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class UploadDAO {
    /**
     * 生成唯一的文件名
     * @param fileName
     * @return
     */
    public static String getUUIDFileName(String fileName){
        /* 将文件名前面的部分去除： xx.jpg  --->   .jpg */
        int idx = fileName.lastIndexOf(".");
        String extention = fileName.substring(idx);

        /* 去掉随机生成文件名中的- */
        String uuidFileName = UUID.randomUUID().toString().replace("-","") + extention;
        return uuidFileName;
    }

    /**
     * 上传视频的路径至数据库
     * @param userId
     * @param videoName
     * @param path
     * @return
     */
    public static int uploadToMySql(int userId, String username, String videoName, String path){
        Connection conn = null;
        PreparedStatement pstmt = null;
        int count = 0;

        try{
            conn = JDBCUtils.getConnection();

            String sql = "INSERT INTO video(videoId,userId,username,videoName,path) VALUE(NULL,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,userId);
            pstmt.setString(2,username);
            pstmt.setString(3, videoName);
            pstmt.setString(4, path);

            count = pstmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.release(pstmt, conn);
        }
        return count;
    }

}
