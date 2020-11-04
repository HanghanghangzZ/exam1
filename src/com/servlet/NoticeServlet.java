package com.servlet;

import com.services.UserServices;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoticeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String noticeName = (String) req.getSession().getAttribute("username");
        String byNoticeName = (String) req.getSession().getAttribute("elseUsername");

        /* 不存在关注与被关注关系 并且 已登录账号才能进行关注 */
        if (UserServices.isNotice(noticeName, byNoticeName) || noticeName == null) {
            resp.sendRedirect("/exam1/ElseHomePageVideoListServlet");
        } else {
            UserServices.notice(noticeName, byNoticeName);
            resp.sendRedirect("/exam1/ElseHomePageVideoListServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
