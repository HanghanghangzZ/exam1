package com.servlet;

import com.bean.Video;
import com.services.UserServices;
import com.services.VideoServices;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 访问其它用户的主页
 */
public class ElseHomePageVideoListServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("调用ElseHomePageVideoListServlet的service方法");
        String elseUsername = "";
        String username = (String) req.getSession().getAttribute("username");
        if (req.getParameter("elseUsername") == null) {
            elseUsername = (String) req.getSession().getAttribute("elseUsername");
        } else {
            elseUsername = req.getParameter("elseUsername");
            /* 将访问的用户的主页的用户名存入seession域，防止在分页查询时，页面跳转导致的无法查询 */
            req.getSession().setAttribute("elseUsername", req.getParameter("elseUsername"));
        }

        if (username != null && UserServices.getUserId(elseUsername) != 0) {    //检查用户输入的用户名是否存在
            /* 获得当前分页的页码 */
            String pageStr = req.getParameter("page");
            System.out.println("pageStr:" + pageStr);
            /* 页码默认为1 */
            int page = 1;
            if (null != pageStr && !"".equals(pageStr)) {
                page = Integer.parseInt(pageStr);
            }
            System.out.println("page:" + page);

            /* 分页查询全部视频 */
            /* 添加访问个人主页的历史记录 */
            UserServices.addHistory(UserServices.getUserId(username), UserServices.getUserId(elseUsername));

            List<Video> videos = VideoServices.getVideos(page, 5, elseUsername);

            int count = VideoServices.countVideo();             //计算总共有多少个视频
            int last = count % 5 == 0 ? (count / 5) : ((count / 5) + 1);      //获得最后一页的页码

            for (Video v : videos) {
                System.out.println(v.getVideoName());
            }

            req.setAttribute("last", last);
            req.setAttribute("videos", videos);
            req.setAttribute("page", page);
            req.getRequestDispatcher("/elseHomePage.jsp").forward(req, resp);
        } else {    /* 用户输入错误，根据不同的页面跳转信息，跳转回过来的页面 */
            System.out.println("访问的用户名不存在或者没有登录");
            req.getSession().setAttribute("visitError", "请先登录或者访问的用户名不存在");
            String information = (String) req.getSession().getAttribute("information");
            System.out.println(information);
            if ("mainPage".equals(information)) {
                req.getRequestDispatcher("/VideoListServlet").forward(req, resp);
            } else if ("elseHomePage".equals(information)) {
                req.getRequestDispatcher("/ElseHomePageVideoListServlet").forward(req, resp);
            } else if ("history".equals(information)) {
                req.getRequestDispatcher("/HistoryListServlet").forward(req, resp);
            } else if ("Noticer".equals(information)) {
                req.getRequestDispatcher("/NoticerListServlet").forward(req, resp);
            } else if ("byNoticer".equals(information)) {
                req.getRequestDispatcher("/ByNoticerListServlet").forward(req, resp);
            }
        }

        System.out.println("ElseHomePageVideoListServlet的service调用结束");
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        System.out.println("调用ElseHomePageVideoListServlet的init方法");
        super.init();
    }
}
