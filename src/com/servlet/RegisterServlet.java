package com.servlet;

import com.DAO.RegisterDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        /* 检查用户名是否已经被注册或是否未在文本框中输入 */
        if (!"".equals(username) && !"".equals(password) && !RegisterDAO.CheckUsername(username)) {                               //用户名未使用
            /* 将注册信息存入数据库 */
            password = RegisterDAO.Encryption(password);                          //对密码进行md5加密
            RegisterDAO.register(username, password);

            /* 注册成功，重定向回登录界面 */
            req.getSession().setAttribute("username", username);                //将username设置在整个会话中
            resp.sendRedirect("/exam1/login.jsp");
            System.out.println("注册成功!");
        } else {                                                                     //用户名已经使用
            /* 用户名已经存在，设置错误信息在regist.jsp页面上显示 */
            req.setAttribute("msg", "用户名已经存在或请在文本框中输入信息！");
            req.getRequestDispatcher("/regist.jsp").forward(req, resp);         //转发回注册页面
            System.out.println("注册失败!");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
