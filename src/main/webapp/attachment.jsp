<%--
  Created by IntelliJ IDEA.
  User: Мария
  Date: 06.03.15
  Time: 9:29
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="style.css">
    <script src="<c:url value="/resources/js/addPhoto.js" />"></script>
    <title>Присоединенные файлы</title>
            </head>
  <body>
  <%
      int id = Integer.valueOf(request.getParameter("id"));
     // pageContext.setAttribute("id", id);

  %>
    <form method = "post" action="FrontController" enctype="multipart/form-data">
            Select file to upload:
            <input type="file" name="uploadFile" id="uploadFile"/>
            <input type="text" name="name" id="name"/>
            <input type="text" name="comment" id="comment"/>
            <br/><br/>
            <input type="hidden" name="command" value="FileUpload">
            <input type="hidden" name="directory" value="attachments">
            <input type="hidden" name="id" id="id" value=<%=id%>>

            <input type="submit" value="Сохранить">
            <%--            <input type="submit" value="Upload">--%>
            </form>
            </body>
</html>