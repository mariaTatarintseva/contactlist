<%--
  Created by IntelliJ IDEA.
  User: Мария
  Date: 19.02.15
  Time: 6:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    pageContext.setAttribute("months", months);

    String[] family = {"NOT SPECIFIED", "DIVORCED", "DATING", "MARRIED", "SINGLE", "WIDOW"};
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
    <h1>Редактирование контакта</h1>
</div>
<div id="container">
    <div id="content">
<form action="FrontController" method = "post" name="adding">
    <input type="hidden" name="command" value="Search">
    <div class="main">
        <div class="field"><label for="name"> Имя*</label> <input name ="name" id="name"></div>
        <div class="field"><label for="surname">Фамилия* </label> <input name ="surname" id="surname" ></div>
        <div class="field"><label for="fathername"> Отчество </label><input name ="fathername" id="fathername" ></div>
        <div class="field"><label for="year">Дата рождения до</label><input type ="text" name ="day" id="day" size="2"> </div>

        <select name="months" size="1">
            <c:forEach var="month" begin="1" end="12">
                <c:choose>
                    <c:when test="${month == choice}">
                        <option value="${month}" selected="selected">${months[month-1]}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${month}">${months[month-1]}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
        <input type ="text" name ="year" id ="year" size="4">


        <div class="field"><label> Пол</label><input type = "radio" name ="gender" value="FEMALE">женский<input type = "radio" name ="gender" value="MALE">мужской</div>
        <div class="field"><label for="citizenship"> Гражданство</label><input type="text" name ="citizenship" id="citizenship" ></div>

        <div class="field"><label for="family"> Семейное положение </label>
            <select name="family" id ="family" size="1">
                <c:forEach var="f" begin="0" end="4">
                    <c:choose>
                        <c:when test="${f == fam}">
                            <option value="${f}" selected="selected">${family[f]}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${f}">${family[f]}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select></div>
        <div class="field"><label>Адрес</label></div>
        <div class="field"><label for="country"> Страна </label><input name ="country" id="country"></div>
        <div class="field"><label for="town"> Город </label><input name ="town" id="town" ></div>
        <div class="field"><label for="street"> Улица </label><input name ="street" id="street"></div>
        <div class="field"><label for="house">Дом</label> <input name ="house" id="house"></div>
        <div class="field"><label for="place"> Квартира </label><input name ="place" id="place"></div>
        <div class="field"><label for="postIndex"> Индекс </label><input name ="postIndex" id="postIndex"></div>

       </div>
    <input type="submit" value="Найти">
</form>
    </div>
</div>
<div id="footer">
    iTechArt 2015
</div>
</body>
</html>