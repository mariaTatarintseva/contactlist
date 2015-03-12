<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Мария
  Date: 18.02.15
  Time: 21:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="style.css">
    <script src="<c:url value="/resources/js/phone.js" />"></script>
    <title>Insert title here</title>
</head>
<body>    <div class="main">

<form method="POST">

        <div class="field"><label for="country">Код страны</label> <input id ="country"></div>
        <div class="field"><label for="operator">Код оператора</label> <input id ="operator"></div>
    <div class="field"><label for="number">Телефонный номер </label><input id ="number"> </div>
    <div class="field"><label for="home">Тип телефона</label> <input type = "radio" name="type" id ="home" checked="checked">Домашний<input name="type" type = "radio" id ="cell">Мобильный</div>
    <div class="field"><label for="comment">Комментарий </label><input id ="comment"></div>
 <input type="submit" value="Сохранить" onclick="addNewPhone()">
 </form>
    </div>
<input type="submit" value="Отменить" id="cancel" onclick="window.close()">
</body>
</html>