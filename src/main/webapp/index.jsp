<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
    import = "java.util.*"
    %>
<%@ page import="servlet.DataAccessObject" %>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="tbl" uri="contacts"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
   <meta charset="utf-8">
   <link rel="stylesheet" type="text/css" href="style.css">
    <script src="<c:url value="/resources/js/index.js" />"></script>
    <title>Список контактов</title>
 </head>

<body onload="initializePages()">
<%
   int pageNumber, onPage;
    onPage=10;
    String ids = "null";
    if (request.getParameter("pageNumber") == null) {
        pageNumber = 0;
    }  else {
        pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
        onPage = Integer.valueOf(request.getParameter("onPage"));
    }
    if (request.getSession().getAttribute("results") != null) {
     //  onPage=0;
        ids = (String) request.getSession().getAttribute("results");
        request.getSession().removeAttribute("results");
    }
   // onPage = 10; //get from properties file
    int total = DataAccessObject.total();

%>




 <div>
  <form action="FrontController">
<div>
      <%if ("null".equals(ids))     { %>

  <tbl:contacts onPage="<%=onPage%>" page="<%=pageNumber%>" />
      <% }  else {%>
      <tbl:result ids="<%=ids%>"/>
      <%}%>
</div>
<input type="hidden" name="command" id='com' value="DefaultValue">
<input type="hidden" id='onPage' value="10">
<input type="hidden" id='page' value="0">
<input type="submit" value="Удалить выбранные" onclick="removeSelected()">
<input type="submit" value="Написать письмо выбранным" onclick="emailToSelected()">
 </form>
     </div>

<form action="add.jsp">
    <%--<input type="hidden" name="command" value="AddContact">--%>
    <input type="submit" value="Добавить контакт">
</form>
<form action="search.jsp">
    <input type="submit" value="Поиск по контактам">
</form>
<table>
    <tr>
    <c:forEach var="i" begin="0" end="<%=(total-1)/onPage%>">
      <td>
<form action="FrontController" name="page">
    <input type="hidden" name="command" value="OpenPage">
    <input type="hidden" name="page" value="${i}">
    <input type="hidden" name="onPage" value="<%=onPage%>">
    <input type="submit"  value="${i+1}" class="just_text">
    <%--<a href='FrontController' onclick="document.page.submit()"> <c:out value="${i}"></c:out> </a>--%>

</form>
      </td>
 </c:forEach>
    </tr>
</table>

</body>
</html>

