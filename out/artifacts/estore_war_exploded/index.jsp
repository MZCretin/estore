<%--
  Created by IntelliJ IDEA.
  User: cretin
  Date: 2017/8/25
  Time: 09:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>主页</title>
</head>
<body>
<h1>Estore</h1>
<hr>
<c:if test="${sessionScope.user==null}">
    欢迎光临，游客
    <a href="${pageContext.request.contextPath}/login.jsp">登录</a>
    <a href="${pageContext.request.contextPath}/register.jsp">注册</a>
</c:if>

<c:if test="${sessionScope.user!=null}">
    欢迎回来，${sessionScope.user.username}<br>
    <a href="${pageContext.request.contextPath}/LogoutServlet">注销</a>
</c:if>
</body>
</html>
