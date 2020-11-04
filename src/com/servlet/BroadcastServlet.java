package com.servlet;

import com.check.InputCheck;
import com.services.VideoServices;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BroadcastServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* 先检查用户输入的字符串中全部都是数字并且这个视频id存在于数据库中再进行 */

        if (InputCheck.checkNum(req.getParameter("videoId"))) {
            int videoId = Integer.parseInt(req.getParameter("videoId"));
            if (VideoServices.checkVideoId(videoId)) {
                String path = VideoServices.getPath(videoId);
                VideoServices.addCount(videoId);

                String username = VideoServices.queryUsername(videoId);
                req.getSession().setAttribute("elseUsername", username);

                req.setAttribute("path", path);
                req.getRequestDispatcher("/broadcast.jsp").forward(req, resp);

                return; //用户输入正确，成功播放视频
            }
        }
        /* 用户输入错误，根据不同的页面跳转信息，跳转回过来的页面 */
        System.out.println("视频id输入错误");
        System.out.println(req.getSession().getAttribute("information"));
        if ("mainPage".equals(req.getSession().getAttribute("information"))) {
            req.getRequestDispatcher("/VideoListServlet").forward(req, resp);
        } else if ("homePage".equals(req.getSession().getAttribute("information"))) {
            req.getRequestDispatcher("/HomePageVideoListServlet").forward(req, resp);
        } else if ("elseHomePage".equals(req.getSession().getAttribute("information"))) {
            req.getRequestDispatcher("/ElseHomePageVideoListServlet").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
