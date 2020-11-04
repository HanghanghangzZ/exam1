<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 杭杭
  Date: 2020/10/26
  Time: 21:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人主页</title>
    <link rel="stylesheet" href="css/homePage.css">
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
    request.getSession().setAttribute("information", "homePage");        //标志是从哪个页面跳转，便于跳转回来

    String username = "";
    if (session.getAttribute("username") != null) {
        username = (String) session.getAttribute("username");
    }
    /* 由上传视频的Servlet设置，如果用户未输入视频名称或未上传视频文件，就会弹出这个警告 */
    String NoVideoName_Path = "";
    if (application.getAttribute("NoVideoName_Path") != null) {
        NoVideoName_Path = (String) application.getAttribute("NoVideoName_Path");
    }

    int pageNow = 1;
%>

<%
    if (username != "") {                       //防止不进行登录直接访问个人主页界面
%>
<h3 align="center">欢迎来到个人主页</h3>
<hr/>
<h3>登录账户:<%= session.getAttribute("username")%>
</h3>
<hr/>
<h3 align="right"><a href="/exam1/VideoListServlet">前往主页</a></h3>
<h3><a href="login.jsp">切换账户</a></h3>
<h3><a href="/exam1/NoticerListServlet">查看关注</a></h3>
<h3><a href="/exam1/ByNoticerListServlet">查看粉丝</a></h3>
<h3><a href="/exam1/HistoryListServlet">谁看过我</a></h3>
<!--
    注意：multipart/form-data不能使用request获得表单信息
    文件上传的必备条件
    * 表单必须是post的提交方式
    * 表单必须有文件上传项，文件上传项必须有name属性值
    * 表单的enctype属性必须设置为 multipart/form-data
-->
<div><font color="red"><%= NoVideoName_Path%>
</font></div>
<form action="/exam1/UploadServlet" method="post" enctype="multipart/form-data">
    <table>
        <tr>
            <td>上传视频</td>
            <td><input type="file" id="video" name="upload"></td>
        </tr>
        <tr>
            <td>为您上传的视频命名</td>
            <td><input type="text" class="input1" name="videoName"></td>
        </tr>
        <tr>
            <td colspan="2">
                <div>
                    <input type="submit" value="上传视频">
                </div>
            </td>
        </tr>
    </table>
</form>
<%--            <video src="./upload/6b6e65fde77341bbb9bb45d3e533f0dc.mp4" controls width="1440px" height="900px"></video>--%>
<!-- 获取 {} 中指定的对象（参数、对象等）的值 -->
<section>
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
</section>
<form action="/exam1/BroadcastServlet" method="get">
    <h3>请输入您想观看的视频的id号<input type="text" name="videoId"/></h3>
    <input type="submit" value="观看视频"/>
</form>
<form action="/exam1/DeleteVideoServlet" method="get">
    <h3>请输入您想删除的视频的id号<input type="text" name="deleteVideoId"/></h3>
    <input type="submit" value="删除视频"/>
</form>

<section class="page">
    <div class="container">
        <div id="pagefy">

            <ul>
                <form id="videoForm" action="/exam1/HomePageVideoListServlet" method="post">
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
<%
} else {
%>
<h3 align="center">请先登录账号!</h3>
<%
    }
%>
</body>
</html>
