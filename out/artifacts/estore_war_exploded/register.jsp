<%--
  Created by IntelliJ IDEA.
  User: cretin
  Date: 2017/8/25
  Time: 16:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Estore注册</title>
    <script type="text/javascript">
        function changeImg(img) {
            img.src = img.src + "?time" + new Date().getTime();
        }

        function checkForm() {
            var canSub = true;
            //1、非空检验
            canSub = checkNull("username", "用户名不能为空") && canSub;
            canSub = checkNull("password", "密码不能为空") && canSub;
            canSub = checkNull("password2", "确认密码不能为空") && canSub;
            canSub = checkNull("nickname", "昵称不能为空") && canSub;
            canSub = checkNull("email", "邮箱不能为空") && canSub;
            canSub = checkNull("valistr", "验证码不能为空") && canSub;

            //2、两次密码一致校验
            var psw1 = document.getElementsByName("password")[0].value;
            var psw2 = document.getElementsByName("password2")[0].value;
            if (psw1 != psw2) {
                document.getElementById("password2_msg").innerHTML = "<font color='red'>两次密码不一致</font>";
                canSub = false;
            }

            //3、邮箱格式校验  \w+@\w+(\.\w+)+$
            var email = document.getElementsByName("email")[0].value;
            if (email != null && email != "" && !/^\w+@\w+(\.\w+)+$/.test(email)) {
                document.getElementById("email_msg").innerHTML = "<font color='red'>邮箱格式不正确</font>";
                canSub = false;
            }
            return canSub;
        }

        function checkNull(name, msg) {
            var objValue = document.getElementsByName(name)[0].value;
            document.getElementById(name + "_msg").innerHTML = "";
            if (objValue == null || objValue == "") {
                document.getElementById(name + "_msg").innerHTML = "<font color='red'>" + msg + "</font>";
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<div align="center">
    <h1>Estore注册</h1>
    <hr>
    <form action="${pageContext.request.contextPath}/RegisterServlet" method="post"
          onsubmit="return checkForm()">
        <table>
            <tr>
                <td>用户名</td>
                <td><input type="text" name="username" value="${param.username}"/></td>
                <td id="username_msg"></td>
            </tr>
            <tr>
                <td>密码</td>
                <td><input type="password" name="password"/></td>
                <td id="password_msg"></td>
            </tr>
            <tr>
                <td>确认密码</td>
                <td><input type="password" name="password2"/></td>
                <td id="password2_msg"></td>
            </tr>
            <tr>
                <td>昵称</td>
                <td><input type="text" name="nickname" value="${param.nickname}"/></td>
                <td id="nickname_msg"></td>
            </tr>
            <tr>
                <td>邮箱</td>
                <td><input type="text" name="email" value="${param.email}"/></td>
                <td id="email_msg"></td>
            </tr>
            <tr>
                <td>验证码</td>
                <td><input type="text" name="valistr"/></td>
                <td id="valistr_msg"><font color="red"> ${msg}</font></td>
            </tr>

            <tr>
                <td><input type="submit" value="注册用户"/></td>
                <td><img src="${pageContext.request.contextPath}/ValiImg" onclick="changeImg(this)"
                         style="cursor: pointer"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
