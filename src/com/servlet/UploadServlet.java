package com.servlet;

import com.DAO.UploadDAO;
import com.DAO.UserDAO;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class UploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* 数据的接收 */
        /* 文件上传基本操作: */
        try {
            String videoName = "";
            String path = "";           //储存的绝对路径
            String broadcastPath = "";  //储存进数据库以及播放用的相对路径
            InputStream inputStream = null;
            OutputStream outputStream = null;

            /* 1.创建一个磁盘文件项工厂对象 */
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();

            /* 2.创建一个核心解析类，用于解析请求 */
            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);

            /* 3.解析request请求，返回的是List集合，List集合中存放的是FileItem对象， */
            /* 每一个FileItem就代表页面中的一个表单项 */
            List<FileItem> list = servletFileUpload.parseRequest(req);

            /* 4.遍历集合，获得每一个FileItem，文件上传项，文件上传功能 */
            /* 注意，对于下面的代码，文件项必须在表单项之前，因为我们要获得视频文件的储存路径 */
            for (FileItem fileItem : list) {
                /* 获得文件上传的名称 */
                String fileName = fileItem.getName();
                /* 文件名非空才进行上传，防止异常的发生 */
                if (!fileItem.isFormField() && !("".equals(fileName))) {
                    /* 通过工具类获得唯一的文件名 */
                    String uuidFileName = UploadDAO.getUUIDFileName(fileName);

                    /* 获得文件上传的输入流 */
                    inputStream = fileItem.getInputStream();

                    /* 获得文件上传的路径 */
                    /* 将输入流对接到输出流 */
                    path = "F:\\IdeaProject\\exam1\\web\\upload/" + uuidFileName;
                    broadcastPath = "./upload/" + uuidFileName;

                } else if (fileItem.isFormField()) {                  //判断是表单项上传
                    videoName = fileItem.getString("UTF-8");

                    if (!videoName.equals("")) {
                        /* 上传文件路径与视频名称至数据库 */
                        String username = (String) req.getSession().getAttribute("username");
                        int userId = UserDAO.getUserId(username);
                        System.out.println("上传视频的用户名为：" + username);
                        System.out.println("上传视频的用户id为：" + userId);

                        /* 上传至数据库 */

                        UploadDAO.uploadToMySql(userId, username, videoName, broadcastPath);

                        System.out.println("上传视频路径至数据库成功!");
                    }
                } else if ("".equals(videoName) || "".equals(fileName)) {       //未输入上传视频的名称或未上传视频文件
                    /* 未设置上传视频的名称，跳转回个人主页 */
                    ServletContext servletContext = req.getSession().getServletContext();
                    servletContext.setAttribute("NoVideoName_Path", "未上传视频文件或未输入视频名称");
                    resp.sendRedirect("/exam1/HomePageVideoListServlet");

                    System.out.println("用户未输入上传视频的名称或未上传视频文件!");

                    return;
                }
            }//for_end
            /* 确保输入了上传视频的名称再上传至储存上传视频的文件夹中 */
            if (!"".equals(videoName)) {
                outputStream = new FileOutputStream(path);

                if (inputStream != null) {
                    int len = 0;
                    byte[] b = new byte[1024];
                    while ((len = inputStream.read(b)) != -1) {
                        outputStream.write(b, 0, len);
                    }
                    System.out.println("上传至文件成功");
                }
                outputStream.close();           //因为当输入了视频名称时，输出流才不是null，所以将其放在里面
            }
            inputStream.close();

            /* 上传视频成功，重定向回个人主页 */
            resp.sendRedirect("/exam1/HomePageVideoListServlet");

        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
