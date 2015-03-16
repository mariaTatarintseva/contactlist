<%--
  Created by IntelliJ IDEA.
  User: Мария
  Date: 28.02.15
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="style.css">
    <script src="<c:url value="/resources/js/addPhoto.js" />"></script>
    <title>Выбрать фото</title>
</head>
<body>
    <%
        String id = request.getParameter("id");
        pageContext.setAttribute("id", id);

    %>


    <form method = "post" action="FrontController" enctype="multipart/form-data">
                Select file to upload:

                <input type="file" name="uploadFile" id="uploadFile" accept="image/*"/>
                <br/><br/>
                <input type="hidden" name="command" value="FileUpload"/>
                <input type="hidden" name="directory" value="avatars"/>
                <input type="hidden" name="id" id="id" value="${id}"/>
       <input type="submit" value="Сохранить"/>
    <%--            <input type="submit" value="Upload"/>--%>
            </form>
</body>
</html>
