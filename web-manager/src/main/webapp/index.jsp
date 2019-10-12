<%@taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@page pageEncoding="UTF-8" language="java" contentType="text/html; ISO-8859-1" isELIgnored="false" %>
<html>
<body>
<h2>Hello World!</h2>
<h2>你好</h2>
<h1>自定义登录页面</h1>
<form action="admin/index.html" method="post">
    <table>
        <tr>
            <td>用户名</td>
            <td><input type="text" name="username"></td>
        </tr>
        <tr>
            <td>密码</td>
            <td><input type="password" name="password"></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="登录">
                <input type="reset" value="重置">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
