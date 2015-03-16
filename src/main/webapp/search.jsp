<%--
  Created by IntelliJ IDEA.
  User: Мария
  Date: 19.02.15
  Time: 6:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    pageContext.setAttribute("months", months);

    String[] family = {"", "NOT SPECIFIED", "DIVORCED", "DATING", "MARRIED", "SINGLE", "WIDOW"};
    pageContext.setAttribute("family", family);


%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="style.css">
    <title></title>
</head>
<body>
<div id="header">
    <h1>Поиск контакта</h1>
</div>
<div id="container">
    <div id="content">
<form action="FrontController" method = "post" name="adding">
    <input type="hidden" name="command" value="Search">
    <div class="main">
        <div class="field"><label for="name"> Имя</label> <input name ="name" id="name" maxlength="20"></div>
        <div class="field"><label for="surname">Фамилия</label> <input name ="surname" id="surname" maxlength="30"></div>
        <div class="field"><label for="fathername"> Отчество </label><input name ="fathername" id="fathername"maxlength="30" ></div>
        <div class="field"><label >Дата рождения (не раньше)</label></div><div><input type ="text" name ="dayL" id="dayL" size="2"> </div>

        <select name="monthsL" size="1">
            <option value="" selected="selected"></option>
            <c:forEach var="month" begin="1" end="12">
                        <option value="${month}">${months[month-1]}</option>
            </c:forEach>
        </select>
        <input type ="text" name ="yearL" id ="yearL" size="4">

        <div class="field"><label>Дата рождения (не позже)</label></div><div><input type ="text" name ="dayU" id="dayU" size="2"> </div>

        <select name="monthsU" size="1">
            <option value="" selected="selected"></option>
            <c:forEach var="month" begin="1" end="12">
                        <option value="${month}">${months[month-1]}</option>
            </c:forEach>
        </select>
        <input type ="text" name ="year" id ="yearU" size="4">

        <div class="field"><label>Дата рождения (точная дата)</label></div><div><input type ="text" name ="day" id="day" size="2"> </div>

        <select name="months" size="1">
            <option value="" selected="selected"></option>
            <c:forEach var="month" begin="1" end="12">
                       <option value="${month}">${months[month-1]}</option>
            </c:forEach>
        </select>
        <input type ="text" name ="year" id ="year" size="4">

        <div class="field"><label> Пол</label><input type = "radio" name ="gender" value="FEMALE">женский<input type = "radio" name ="gender" value="MALE">мужской</div>
        <div class="field"><label for="citizenship"> Гражданство</label><input type="text" name ="citizenship" id="citizenship"maxlength="30" ></div>

        <div class="field"><label for="family"> Семейное положение </label></div>
            <select name="family" id ="family" size="1">
                <c:forEach var="f" begin="0" end="4">
                             <option value="${f}">${family[f]}</option>
                </c:forEach>
            </select>
        <div class="field"><label>Адрес</label></div>
        <div class="field"><label for="country"> Страна </label><input name ="country" id="country" maxlength="20"></div>
        <div class="field"><label for="town"> Город </label><input name ="town" id="town" maxlength="20"></div>
        <div class="field"><label for="street"> Улица </label><input name ="street" id="street" maxlength="20"></div>
        <div class="field"><label for="house">Дом</label> <input name ="house" id="house" maxlength="10"></div>
        <div class="field"><label for="place"> Квартира </label><input name ="place" id="place" maxlength="10"></div>
        <div class="field"><label for="postIndex"> Индекс </label><input name ="postIndex" id="postIndex" maxlength="10"></div>
    </div>
    <input type="submit" value="Найти" class="btnClass">
</form>
    </div>
</div>
<div id="footer">
    iTechArt 2015
</div>
</body>
</html>