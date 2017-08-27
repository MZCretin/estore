<%--
  Created by IntelliJ IDEA.
  User: Cretin
  Date: 2017/8/26
  Time: 23:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Estore登录</title>
    <script type="text/javascript">
        window.onload = function () {
            var str = decodeURI('${cookie.remename.value}');
            document.getElementsByName("username")[0].value = str;
        }
    </script>
</head>
<body>
<div align="center">
    <h1>Estore登录</h1>
    <hr>
    <font color="red">${msg }</font>
    <form action="/LoginServlet" method="post">
        <table border="1">
            <tr>
                <td>用户名：</td>
                <td><input type="text" name="username"/></td>
            </tr>
            <tr>
                <td>密码：</td>
                <td><input type="password" name="password"/></td>
            </tr>
            <tr>
                <td><input type="checkbox" value="true" name="remename"
                        <c:if test="${cookie.remename!=null}">
                            checked = "checked"
                        </c:if>/>记住用户名</td>
                <td><input type="checkbox" value="true" name="autologin"
                        <c:if test="${cookie.autologin!=null}">
                            checked = "checked"
                        </c:if>/>30天自动登录</td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="登录"/></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
