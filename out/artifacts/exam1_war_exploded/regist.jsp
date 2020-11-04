<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注册</title>
<link rel="stylesheet" href="css/reg.css">
</head>
<body>
    <div class="reg">
        <div class="header">
            <h1>
                <a href="login.jsp">登录</a> <a href="./regist.jsp">注册</a>
            </h1>
        </div>

        <%
            /* 由注册的Servlet设置，如果用户名重复就会有这个值，并弹出警告 */
            String msg = "";
            if (request.getAttribute("msg") != null){
                msg = (String) request.getAttribute("msg");
            }
        %>

        <div><font color="red"><%= msg%></font></div>
        <form action="/exam1/RegisterServlet" method="post">
            <table>
                <tr>
                    <td class="td1">用户名</td>
                    <td><input type="text" class="input1" name="username"></td>
                </tr>
                <tr>
                    <td class="td1">密码</td>
                    <td><input type="password" class="input1" name="password"></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div class="btn-red">
                            <input type="submit" value="注册" id="reg-btn">
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>