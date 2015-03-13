<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="dataclasses.Contact" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.DataAccessObject" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="style.css">
    <script src="<c:url value="/resources/js/email.js" />"></script>
<title>Написать письмо</title>
</head>
<body>
<%
    String[] idsS=request.getParameterValues("to");
    ArrayList<Integer> ids = new ArrayList<Integer>();
    for (int i = 0; i<idsS.length; ++i) {
        ids.add(Integer.valueOf(idsS[i]));
    }
    pageContext.setAttribute("ids", ids.toArray());
    ArrayList<Contact> contacts = DataAccessObject.getFromDatabase(ids);
    ArrayList<Contact> empty = new ArrayList<Contact>();
    for (Contact contact: contacts) {
        if (StringUtils.isEmpty(contact.getEmail())) {
            empty.add(contact);
        }
    }
    contacts.removeAll(empty);
    String[] mails = new String[contacts.size()];
    for (int i = 0; i<mails.length; ++i) {
        mails[i] = contacts.get(i).getEmail();
    }

    String sendTo = StringUtils.join(mails, ", ");

    String[] templates = {"Приветствие и подпись", "Дата и подпись"};
    pageContext.setAttribute("templates", templates);

%>
<div id="header">
    <h1>Написать письмо</h1>
</div>
<div id="container">

    <div id="content">
<form action="FrontController">
    <input type="hidden" name="command" value="SendEmail">
    <input type="hidden" name="arg2" value="">
    <div class="main">
        <div class="field"><label for="adresses">Получатели: </label> <input type="text" name="adresses" id="adresses" value="<%=sendTo%>"></div>
        <div class="field"><label for="header">Тема: </label> <input type="text" name="header" id="sujet"></div>
        <textarea name="mailText" rows="10" cols="70" onkeydown="updateText()" id="message"></textarea>
        <textarea disabled="disabled" id="view" rows="10" cols="70"></textarea>
    </div>

    <c:forEach var="i" items="${pageScope.ids}">

        <input type="hidden" name="to" value="${i}">
    </c:forEach>
    <input type="submit" value="Отправить">
</form>
</div>
    <div id="footer">
        iTechArt 2015
    </div>
    </div>
</body>
</html>