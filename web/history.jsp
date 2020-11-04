<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 杭杭
  Date: 2020/11/1
  Time: 19:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>访问历史记录</title>
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
            document.getElementById('userForm').submit();
        }
    </script>
</head>
<body>
    <%
        request.getSession().setAttribute("information","history");
    %>
    <a href="/exam1/HomePageVideoListServlet">返回个人主页</a>
    <table>
        <tr>
            <td>用户id</td>
            <td>用户名</td>
            <td>访问时间</td>
        </tr>
        <c:forEach items="${users}" var="x">
            <tr>
                <td>${x.id}</td>
                <td>${x.username}</td>
                <td>${x.date}</td>
            </tr>
        </c:forEach>
    </table>
    <form action="/exam1/ElseHomePageVideoListServlet" method="get">
        <h3>请输入您想访问的个人中心的用户名<input type="text" name="elseUsername"/></h3>
        <input type="submit" value="访问主页"/>
    </form>

    <ul>
        <form id="userForm" action="/exam1/HistoryListServlet" method="post">
            <input type="hidden" id="page" name="page" value="${page}">
            <input type="hidden" id="last" name="last" value="${last}">
            <li><a href="javascript:void(0)" onclick="submitVideoForm('first')">首页</a></li>
            <li><a href="javascript:void(0)" onclick="submitVideoForm('pre')">上一页</a></li>
            <li><a href="javascript:void(0)">当前第${page}页</a></li>
            <li><a href="javascript:void(0)" onclick="submitVideoForm('next')">下一页</a></li>
            <li><a href="javascript:void(0)" onclick="submitVideoForm('last')">尾页</a></li>
        </form>
    </ul>

</body>
</html>
