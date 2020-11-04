<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 杭杭
  Date: 2020/10/31
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>网站主页</title>
    <script type="text/javascript">
        function submitVideoForm(flag) {
            if ('first' == flag) {
                document.getElementById('page').value = 1;
            } else if ('pre' == flag) {
                var current = Number(document.getElementById('page').value);
                if (current > 1) {
                    document.getElementById('page').value = current - 1;
                }
            } else if ('next' == flag) {
                var current = Number(document.getElementById('page').value);
                var last = Number(document.getElementById('last').value);
                if (current < last) {
                    document.getElementById('page').value = current + 1;
                }
            } else if ('last' == flag) {
                var last = Number(document.getElementById('last').value);
                document.getElementById('page').value = last < 1 ? 1 : last;
            }
            document.getElementById('videoForm').submit();
        }
    </script>
</head>
<body>
    <%
        request.getSession().setAttribute("information", "mainPage");        //标志是从哪个页面跳转，便于跳转回来

        String username = "";
        if (session.getAttribute("username") != null) {
            username = (String) session.getAttribute("username");
        }

        if ("".equals(username)){
    %>
        <h3><a href="login.jsp">登录账户</a></h3>
    <%
        }else {
    %>
        <h3>登录账户<%= session.getAttribute("username")%></h3>
        <h3><a href="/exam1/HomePageVideoListServlet">个人主页</a></h3>
        <h3><a href="login.jsp">切换账户</a></h3>
    <%
        }
    %>

<%--                <video src="./upload/eeb7b29bc8f14e78a0f18845c3efdf94.mp4" controls width="1440px" height="900px"></video>--%>
    <!-- 获取 {} 中指定的对象（参数、对象等）的值 -->
    <h3 align="center">本站视频播放量排名</h3>
    <div align="center">
        <table>
            <tr>
                <td>视频名称</td>
                <td>视频id</td>
                <td>视频上传者</td>
                <td>视频上传者id</td>
                <td>视频播放量</td>
            </tr>
            <c:forEach items="${videos}" var="x">
                <tr>
                    <td>${x.videoName}</td>
                    <td>${x.videoId}</td>
                    <td>${x.username}</td>
                    <td>${x.userId}</td>
                    <td>${x.count}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <section class="page">
        <div class="container">
            <div id="pagefy">

                <ul>
                    <form id="videoForm" action="/exam1/VideoListServlet" method="post">
                        <input type="hidden" id="page" name="page" value="${page}">
                        <input type="hidden" id="last" name="last" value="${last}">
                        <li><a href="javascript:void(0)" onclick="submitVideoForm('first')">首页</a></li>
                        <li><a href="javascript:void(0)" onclick="submitVideoForm('pre')">上一页</a></li>
                        <li><a href="javascript:void(0)">当前第${page}页</a></li>
                        <li><a href="javascript:void(0)" onclick="submitVideoForm('next')">下一页</a></li>
                        <li><a href="javascript:void(0)" onclick="submitVideoForm('last')">尾页</a></li>
                    </form>
                </ul>

            </div>
        </div>
    </section>

    <form action="/exam1/BroadcastServlet" method="get">
        <h3>请输入您想观看的视频的id号<input type="text" name="videoId"/></h3>
        <input type="submit" value="观看视频"/>
    </form>
    <%
        String visitError = "";
        if (request.getSession().getAttribute("visitError") != null) {
            visitError = (String) request.getSession().getAttribute("visitError");
        }
    %>
    <font color="red"><%= visitError%></font>
    <form action="/exam1/ElseHomePageVideoListServlet" method="get">
        <h3>请输入您想访问的个人中心的用户名<input type="text" name="elseUsername"/></h3>
        <input type="submit" value="访问主页"/>
    </form>


</body>
</html>
