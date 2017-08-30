<%--
  Created by IntelliJ IDEA.
  User: cretin
  Date: 2017/8/28
  Time: 13:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Estore所有商品</title>
</head>
<body>
<div align="center">
    <h1>Estore_所有产品</h1>
    <hr>
    <table width="100%">
        <c:forEach items="${requestScope.list}" var="product">
            <tr>
                <td width="40%"><a href="/ProductInfoServlet?id=${product.id}"><img
                        src="/ImageServlet?imgurl=${product.imgurls}"></a></td>
                <td width="40%">
                    商品名称：${product.name}<br>
                    商品价格：${product.price }<br>
                    商品分类：${product.category }<br>
                    商品库存：${product.pnum}
                </td>
                <td width="20%">
                    <c:if test="${product.pnum>0}">
                        <font color="blue">有货</font>
                    </c:if>
                    <c:if test="${product.pnum<=0}">
                        <font color="red">缺货</font>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <hr>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
