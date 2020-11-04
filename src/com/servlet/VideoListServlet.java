package com.servlet;

import com.bean.Video;
import com.services.VideoServices;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class VideoListServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("调用VideoListServlet的init方法");
        super.init();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("调用VideoListServlet的service方法");
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
        List<Video> videos = VideoServices.getVideos(page, 5);
        int count = VideoServices.countVideo();             //计算总共有多少个视频
        int last = count % 5 == 0 ? (count / 5) : ((count / 5) + 1);      //获得最后一页的页码

        /* 打印至控制台，观察 */
        for(Video v : videos){
            System.out.println(v.getVideoName());
        }

        req.setAttribute("last", last);
        req.setAttribute("videos", videos);
        req.setAttribute("page", page);
        req.getRequestDispatcher("/mainPage.jsp").forward(req, resp);

        System.out.println("VideoListServlet的service调用结束");
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}