<%@ page import="com.company.Service.LangBundle" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: tyuly
  Date: 09.02.2017
  Time: 18:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%LangBundle.bundle.getString("info");%></title>
</head>
<body>
<h1>${info}</h1>
</body>
</html>
