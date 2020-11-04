<%@ page import="com.services.VideoServices" %><%--
  Created by IntelliJ IDEA.
  User: 杭杭
  Date: 2020/10/31
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>视频播放</title>
</head>
<body>
    <h3><a href="/exam1/VideoListServlet">返回主页</a></h3>
    <h3>视频上传者:<%= request.getSession().getAttribute("elseUsername")%></h3>
    <h3><a href="/exam1/ElseHomePageVideoListServlet">访问TA的个人主页</a></h3>
    <h3><a href="/exam1/NoticeServlet">关注TA</a></h3>
    <video src="<%= request.getAttribute("path")%>" controls width="1280px" height="1024px"></video>
</body>
</html>
