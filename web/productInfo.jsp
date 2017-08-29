<%--
  Created by IntelliJ IDEA.
  User: cretin
  Date: 2017/8/28
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商品详情</title>
</head>
<body>
<div align="center">
    <h1>${product.name}</h1>
    <hr>
    <table width="100%">
        <tr>
            <td><img src="/ImageServlet?imgurl=${product.imgurl}"></td>
            <td>
                商品名称：${product.name}<br>
                商品分类：${product.category}<br>
                商品库存：${product.pnum}<br>
                商品价格：${product.price}<br>
                商品描述：${product.description}
                <a href="/AddCartServlet?id=${product.id}"><img src="/img/buy.bmp"/></a>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
