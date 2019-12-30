<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: liuhaiyan05
  Date: 2019-12-30
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>customerList</title>
</head>
<body>

    <h1>客户列表</h1>
    <table border="1" cellspacing="0" width="600" align="left">
        <tr align="left">
            <th>客户名称</th>
            <th>联系人</th>
            <th>电话号码</th>
            <th>邮箱地址</th>
            <th>操作</th>
        </tr>
        <c:forEach var="customer" items="${customers}">
            <tr>
                <td>${customer.name}</td>
                <td>${customer.contact}</td>
                <td>${customer.telephone}</td>
                <td>${customer.email}</td>
                <td>
                    <a href="${BASE}/customer_edit?id=${customer.id}">编辑</a>
                    <a href="${BASE}/customer_delete?id=${customer.id}">删除</a>
                </td>
            </tr>
        </c:forEach>

    </table>
</body>
</html>