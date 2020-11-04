package com.servlet;

import com.bean.User;
import com.services.UserServices;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 获得粉丝名单
 */
public class ByNoticerListServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("调用ByNoticerListServlet的service方法");

        int userId = UserServices.getUserId((String) req.getSession().getAttribute("username"));
        /* 获得当前分页的页码 */
        String pageStr = req.getParameter("page");

        /* 页码默认为1 */
        int page = 1;
        if (null != pageStr && !"".equals(pageStr)) {
            page = Integer.parseInt(pageStr);
        }

        /* 分页查询粉丝 */
        int pageSize = 5;
        List<User> users = UserServices.getByNoticerUsers(page, pageSize, userId);
        int count = UserServices.countByNoticer(userId);
        int last = count % pageSize == 0 ? (count / pageSize) : (count / pageSize + 1);

        req.setAttribute("last", last);
        req.setAttribute("users", users);
        req.setAttribute("page", page);
        req.getRequestDispatcher("/byNoticer.jsp").forward(req, resp);

        System.out.println("ByNoticerListServlet的service调用结束");
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }
}
