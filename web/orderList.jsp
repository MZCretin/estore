<%--
  Created by IntelliJ IDEA.
  User: cretin
  Date: 2017/8/30
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>订单列表</title>
</head>
<body>
<div align="center">
    <h1>Estore_订单列表</h1>
    <hr>
    <c:forEach items="${requestScope.list}" var="olf">
        <h3>订单号：${olf.id}<br></h3>
        </h3>
        用户名称 :${olf.username }<br>
        订单金额 :${olf.money }<br>
        支付状态 :
        <c:if test="${olf.paystate == 0}">
            <font color="red">未支付</font>
            <a href="/DeleteOrderServlet?id=${olf.id}">删除订单</a>
        </c:if>
        <c:if test="${olf.paystate != 0}">
            <font color="blue">已支付</font>
        </c:if>
        <br>
        收货地址 :${olf.receiverinfo }<br>
        下单时间 :${olf.ordertime }<br>
        <table width="100%" border="1">
            <tr>
                <th width="10%">商品图片</th>
                <th width="18%">商品名称</th>
                <th width="18%">商品种类</th>
                <th width="18%">购买数量</th>
                <th width="18%">商品单价</th>
                <th width="18%">总金额</th>
            </tr>
            <c:forEach items="${olf.prodMap}" var="entry">
                <tr>
                    <td width="10%"><img src="/ImageServlet?imgurl=${entry.key.imgurls}"></td>
                    <td width="18%">${entry.key.name }</td>
                    <td width="18%">${entry.key.category }</td>
                    <td width="18%">${entry.value }</td>
                    <td width="18%">${entry.key.price }</td>
                    <td width="18%">${entry.key.price * entry.value }</td>
                </tr>
            </c:forEach>
        </table>
        <hr>
    </c:forEach>
</div>
</body>
</html>
