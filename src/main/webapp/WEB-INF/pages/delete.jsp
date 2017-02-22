 <%@ page import="com.company.Service.LangBundle" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
</head>
<body>
<form action="<%=request.getContextPath()%>/crud/user"  method="post">
    <input type="hidden" name="_method" value="DELETE"/><input type="submit" value="delete">
<h1></h1>
<div id="login-box-name"><%=LangBundle.bundle.getString("id")%></div>
    <div id="login-box-field">
        <input name="id" type="id" class="form-login" title="id" value="" size="30" maxlength="48" />
    </div>

<input style="margin-left:100px;" type="submit" value=<%=LangBundle.bundle.getString("delete")%> />
    </form>
</body>
</html>
