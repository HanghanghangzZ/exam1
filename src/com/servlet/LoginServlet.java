package com.servlet;

import com.DAO.LoginDAO;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 校验用户名与密码的Servlet
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (!"".equals(username) && !"".equals(password) && LoginDAO.check(username, password)){                      //登录成功
            /* 重定向到个人首页 */
            req.getSession().setAttribute("username", username);     //将用户名信息参数设置在整个会话中
            resp.sendRedirect("/exam1/HomePageVideoListServlet");

            /* 判断复选框是否被勾选 */
            String remember = (String) req.getAttribute("remember");
            if ("true".equals(remember)){
                /* 使用Cookie完成记住用户名的功能，但是服务器关闭再开启后，Cookie就失效了 */
                Cookie cookie = new Cookie("username", username);
                cookie.setPath("/exam1");                                   //设置有效路径
                cookie.setMaxAge(60 * 60 * 24);                             //设置有效时间，单位为秒，这里设置为24小时
                resp.addCookie(cookie);                                     //将Cookie回写到浏览器
            }

            System.out.println("登录成功!");
        }else{                                                          //登录失败
            /* 设置错误信息，并转发回登录页面 */
            req.setAttribute("msg", "用户名不存在或密码错误!");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);

            System.out.println("登录失败!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
