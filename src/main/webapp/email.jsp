<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="servlet.Contact" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="servlet.DataAccessObject" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="style.css">
<title>Написать письмо</title>
</head>
<body>
<%
    String[] idsS=request.getParameterValues("to");
    ArrayList<Integer> ids = new ArrayList<Integer>();
    for (int i = 0; i<idsS.length; ++i) {
        ids.add(Integer.valueOf(idsS[i]));
    }
    ArrayList<Contact> contacts = DataAccessObject.getFromDatabase(ids);
    String[] mails = new String[contacts.size()];
    for (int i = 0; i<mails.length; ++i) {
        mails[i] = contacts.get(i).getEmail();
    }
    pageContext.setAttribute("mails", mails);
    String sendTo = StringUtils.join(mails, ", ");
  //  String[] templates = {"Подпись", "Дата и подпись", "Дата, подпись, контакты"};
  //  pageContext.setAttribute("templates", templates);

%>
<form action="FrontController">
    <input type="hidden" name="command" value="SendEmail">
    <div>
    <input type="text" name="adresses" value="<%=sendTo%>">
    </div>
    <div>
    <input type="text" name="header">
    </div>

    <%--<select name="templates" size="<%=templates.length%>">--%>
        <%--<c:forEach var="template" begin="0" end="<%=templates.length-1%>">--%>
            <%--<option value="${template}">${templates[template]}</option>--%>
        <%--</c:forEach>--%>
    <%--</select>--%>
    <textarea name="mailText" rows="10" cols="70"></textarea>
    <c:forEach var="adr" items="${mails}">
        <input type="hidden" name="to" value="${adr}">
    </c:forEach>
    <input type="submit" value="Отправить">
</form>
</body>
</html>