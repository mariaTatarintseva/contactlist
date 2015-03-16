<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
    import = "java.util.*"
    %>
<%@ page import="dao.DataAccessObject" %>
<%@ page import="java.io.InputStream" %>
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

<body>
<%--// onload="initializePages()">--%>
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

    }
    if (request.getParameter("pageNumber") != null) {
    onPage = Integer.valueOf(request.getParameter("onPage"));
    }
    pageContext.setAttribute("onPage", onPage);
    if (request.getSession().getAttribute("results") != null) {
        ids = (String) request.getSession().getAttribute("results");
        request.getSession().removeAttribute("results");
    }
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

  <form action="FrontController" id="cntForm">

      <%if ("null".equals(ids))     { %>

  <tbl:contacts onPage="<%=onPage%>" page="<%=pageNumber%>" />
      <% }  else {%>
      <tbl:result ids="<%=ids%>"/>
      <%}%>

<input type="hidden" name="command" id='com' value="DefaultValue">


 </form>
         <div class="rightFlow">
<input type="submit" value="Удалить выбранные" onclick="removeSelected()" class="btnClass" form="cntForm">
<button type="button" onclick="emailToSelected()" class="btnClass">Написать письмо выбранным</button>
         </div>

        <%if ("null".equals(ids))     { %>
<table>
    <tr>
    <c:forEach var="i" begin="0" end="<%=(total-1)/onPage%>">
      <td>
<form action="FrontController" name="pageForm" id="pageForm">
    <input type="hidden" name="command" value="OpenPage">
    <input type="hidden" name="page" id="page" value="${i}">
    <input type="hidden" name="onPage" id="onPage" value="<%=onPage%>">
    <input type="submit"  value="${i+1}" class="just_text">
    <%--<a href='FrontController' onclick="document.page.submit()"> <c:out value="${i}"></c:out> </a>--%>

</form>
      </td>
 </c:forEach>
    </tr>
</table>
         <div>
             <label for="selectPage">Показывать на странице:</label>
             <select form="pageForm" onchange="selectThePage()" id="selectPage">
                 <c:choose>
                     <c:when test="${onPage == 10}">
                         <option value="10" selected="selected">10</option>
                         <option value="20">20</option>
                     </c:when>
                     <c:otherwise>
                         <option value="10">10</option>
                         <option value="20" selected="selected">20</option>
                     </c:otherwise>
                 </c:choose>
             </select>
         </div>
         <%}%>
 </div>

<div id="footer">
    iTechArt 2015
</div>
</div>
</body>
</html>

