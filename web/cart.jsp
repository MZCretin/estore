<%--
  Created by IntelliJ IDEA.
  User: cretin
  Date: 2017/8/28
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>我的购物车</title>
    <script type="text/javascript">
        function changeNum(id, obj, oldnum) {
            if (!/^[1-9]\d*$/.test(obj.value)) {
                alert("请输入合法的数字！")
                obj.value = oldnum;
                return;
            } else {
                window.location.href = "/ChangeCartServlet?id=" + id + "&buynum=" + obj.value;
            }
        }
    </script>
</head>
<body>
<div align="center">
    <h1>我的购物车</h1>
    <hr>
    <c:if test="${empty sessionScope.cartmap}">
        <h2><a href="/ProductListServlet">购物车空空如也，快去买点东西吧！</a></h2>
    </c:if>
    <c:if test="${not empty sessionScope.cartmap}">
        <div align="right">
            <a href="/ProductListServlet">继续购物</a> <a href="/ClearCartServlet">清空购物车</a> <a
                href="/addOrder.jsp">进入结算中心</a>
        </div>
        <table width="100%" border="1" style="text-align: center">
            <tr>
                <th>商品图片</th>
                <th>商品名称</th>
                <th>商品种类</th>
                <th>商品单价</th>
                <th>商品数量</th>
                <th>商品状态</th>
                <th>总价</th>
                <th>操作</th>
            </tr>
            <c:set var="money" value="0"></c:set>
            <c:forEach items="${sessionScope.cartmap}" var="product">
                <tr>
                    <td><img src="/ImageServlet?imgurl=${product.key.imgurls}"/></td>
                    <td>${product.key.name}</td>
                    <td>${product.key.category}</td>
                    <td>${product.key.price}</td>
                    <td><input id="buynum" type="text" value="${product.value}" style="width: 30px"
                               onchange="changeNum('${product.key.id}',this,${product.value})"/>件
                    </td>
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
                    <td><a href="/DeleteProduct?id=${product.key.id}">删除</a></td>
                </tr>
            </c:forEach>
        </table>

        <div align="right">
            <font color="red" size="5">总价：￥${money}元</font>
        </div>
    </c:if>
</div>
</body>
</html>
