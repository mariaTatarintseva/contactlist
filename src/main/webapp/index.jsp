<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
    import = "java.util.*"
    %>
<%@ page import="dao.DataAccessObject" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.File" %>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="tbl" uri="contacts"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
   <meta content="text/html; charset=utf-8">
   <link rel="stylesheet" type="text/css" href="style.css">
    <script src="<c:url value="/resources/js/index.js" />"></script>
    <title>Список контактов</title>
 </head>

<body onload="initializePages()">
<%
    if (!DataAccessObject.isInit()) {
        Properties properties = new Properties();
        InputStream inputStream =request.getServletContext().getResourceAsStream("/WEB-INF/properties/contactlist.properties");
        properties.load(inputStream);
        DataAccessObject.setUSER(properties.getProperty("databaseUser"));
        DataAccessObject.setPASSWORD(properties.getProperty("databasePassword"));
        DataAccessObject.setURL(properties.getProperty("databaseURL"));
        DataAccessObject.setDRIVER(properties.getProperty("databaseDriver"));
        DataAccessObject.setInit(true);
    }
   // System.out.println(request.getServletContext().getRealPath("..\\..\\src"));
  //  System.out.println(System.getProperty("catalina.base"));
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


<div id="header">
    <h1>Список контактов</h1>
</div>
<div id="container">
<div id = "nav">
    <a href="add.jsp" class="ref">Добавить контакт</a> <br>
    <%--<input type="hidden" name="command" value="AddContact">--%>
    <%--<input type="submit" value="">--%>
    <a href="search.jsp" class="ref">Поиск по контактам</a>
</div>

     <div id="content">

  <form action="FrontController">

      <%if ("null".equals(ids))     { %>

  <tbl:contacts onPage="<%=onPage%>" page="<%=pageNumber%>" />
      <% }  else {%>
      <tbl:result ids="<%=ids%>"/>
      <%}%>

<input type="hidden" name="command" id='com' value="DefaultValue">
<input type="hidden" id='onPage' value="10">
<input type="hidden" id='page' value="0">

<input type="submit" value="Удалить выбранные" onclick="removeSelected()">

 </form>
<button type="button" onclick="emailToSelected()">Написать письмо выбранным</button>
<p>
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
</table> </p>
 </div>

<div id="footer">
    iTechArt 2015
</div>
</div>
</body>
</html>

