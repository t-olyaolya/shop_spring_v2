<%@ page import="com.company.Service.LangBundle" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
</head>
<body>
<h1>New payment</h1>
<f:form method="POST" commandName="user" action="users">
    <table>
        <tr>
        <tr>
            <td>
                <i><%=LangBundle.bundle.getString("name")%></i>
            </td>
            <td>
                <f:input path="name"/>
            </td>
        </tr>
        <tr>
            <td>
                <i><%=LangBundle.bundle.getString("password")%></i>
            </td>
            <td>
                <f:input path="password"/>
            </td>
        </tr>
        <tr>
            <td><input type="submit"/></td>
        </tr>
    </table>
</f:form>
</body>
</html>
