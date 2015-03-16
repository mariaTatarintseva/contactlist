<%--
  Created by IntelliJ IDEA.
  User: Мария
  Date: 27.02.15
  Time: 10:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title></title>
</head>
<body>
<%
    String[] messages = {"Произошла ошибка при работе с базой данных", "Произошла ошибка при перенаправлении запроса приложению"};
%>
       <h1>
           Извините, произошла ошибка. :(
       </h1>

        <% if (request.getParameter("msg") != null) {
         int i = Integer.valueOf(request.getParameter("msg"));%>
            <h2>
        <div> <%= messages[i]%></div>
            </h2>
        <%}%>
        <h2>
            Мы постараемся исправить проблему как можно быстрее. Попробуйте <a href="index.jsp">вернуться на главную страницу</a>
        </h2>
</body>
</html>