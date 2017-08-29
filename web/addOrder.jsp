<%--
  Created by IntelliJ IDEA.
  User: cretin
  Date: 2017/8/29
  Time: 08:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>生成订单</title>
</head>
<body>
<div align="center">
    <h1>生成订单</h1>
    <hr>
    <form action="/AddOrderServlet" method="post">
        收货地址：<textarea rows="5" cols="45" name="receiverinfo"></textarea><br>
        支付方式：<input type="radio" name="typex" checked="checked"/>在线支付<br>
        <input type="submit" value="生成订单"/>
    </form>
    购物清单：<br>
    <table width="100%" border="1" style="text-align: center">
        <tr>
            <th>商品名称</th>
            <th>商品种类</th>
            <th>商品单价</th>
            <th>商品数量</th>
            <th>商品状态</th>
            <th>总价</th>
        </tr>
        <c:set var="money" value="0"></c:set>
        <c:forEach items="${sessionScope.cartmap}" var="product">
            <tr>
                <td>${product.key.name}</td>
                <td>${product.key.category}</td>
                <td>${product.key.price}</td>
                <td>${product.value}件</td>
                <td>
                    <c:if test="${product.value>product.key.pnum}">
                        <font color="red">缺货</font>
                    </c:if>
                    <c:if test="${product.value<=product.key.pnum}">
                        <font color="blue">有货</font>
                    </c:if>
                </td>
                <td>${product.key.price*product.value}元
                    <c:set var="money"
                           value="${money+product.key.price*product.value}"></c:set></td>
            </tr>
        </c:forEach>
    </table>
    <div align="right">
        <font color="red" size="5">总价：￥${money}元</font>
    </div>
</div>
</body>
</html>
