<%--
  Created by IntelliJ IDEA.
  User: Мария
  Date: 19.02.15
  Time: 6:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="style.css">
    <title></title>
</head>
<body>
<form action="FrontController" method = "post" name="adding">
    <input type="hidden" name="command" value="Search">
    <p>Имя <input name ="name"></p>
    <p>Фамилия* <input name ="surname"></p>
    <p>Отчество <input name ="fathername"></p>
    <p>Дата рождения с <input name ="birthdayFrom"> по <input name ="birthdayFrom"></p>
    <p>Пол <input type = "radio" name ="gender">женский<input type = "radio" name ="gender">мужской</p>
    <p>Семейное положение <input name ="family"></p>
    <p>Гражданство <input name ="citizenship"></p>
     <br>Адрес
    <p>Страна <input name ="country"></p>
    <p>Город <input name ="town"></p>
    <p>Улица <input name ="street"></p>
    <p>Дом <input name ="house"></p>
    <p>Квартира <input name ="place"></p>
    <p>Индекс <input name ="index"></p>
    <input type="submit" value="Найти">
</form>
</body>
</html>