package com.servlet;

import com.check.InputCheck;
import com.services.VideoServices;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 删除视频的servlet
 */
public class DeleteVideoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (InputCheck.checkNum(req.getParameter("deleteVideoId"))) {
            int deleteVideoId = Integer.parseInt(req.getParameter("deleteVideoId"));
            if (VideoServices.checkVideoId(deleteVideoId)) {
                VideoServices.deleteVideo(deleteVideoId);
                req.getRequestDispatcher("/HomePageVideoListServlet").forward(req, resp);
            }
            return; //用户输入正确，删除视频
        }
        /* 用户输入错误，跳转回去 */
        req.getRequestDispatcher("/HomePageVideoListServlet").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
