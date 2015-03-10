<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tbl" uri="contacts" %>
<%@ page import="servlet.Contact" %>
<%@ page import="com.mysql.jdbc.StringUtils" %>
<%@ page import="servlet.PhoneNumber" %>
<%@ page import="java.io.File" %>
<%@ page import="servlet.DataAccessObject" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
   <link rel="stylesheet" type="text/css" href="style.css">
    <script src="<c:url value="/resources/js/add.js" />"></script>
<title>Добавить контакт</title>
</head>
<body>


<%
    Contact contact;
    String buttonText = "Сохранить";
//    String phones="";
//    String types="";
//    String comments="";
    Integer contactId = null;
 //   ArrayList<PhoneNumber> phoneNumbers;
    if (request.getParameter("id") == null) {
        contact = new Contact();
        contact.setName("");
        contact.setSurname("");
   //     phoneNumbers = new ArrayList<PhoneNumber>();
        buttonText = "Добавить контакт";
    }    else {
        contact = DataAccessObject.getFromDatabase(Integer.valueOf(request.getParameter("id")));
        contactId = Integer.valueOf(request.getParameter("id"));
    }
    String photo=null;
    if (request.getSession().getAttribute("photo") != null) {
        photo = (String) request.getSession().getAttribute("photo");
        request.getSession().removeAttribute("photo");
        contact.setPhoto(photo);
    } else if (contactId != null) {
        photo = contact.getPhoto();
    }
    System.out.print("Photo: " + photo);
 /*   String imagePath = (new File(request.getServletContext().getRealPath("")
            + File.separator + UPLOAD_DIRECTORY +File.separator + contact.getId() + ".gif" )).exists() ?
            "avatars/" + contact.getId() + ".gif" : "img/incognito.gif";
     System.out.println("Size: " + contact.getPhoneNumbers().size());    */
    String[] months = {"января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"};
    pageContext.setAttribute("months", months);
    ArrayList<Integer> list = DataAccessObject.getPhonesOf(contactId);
    Integer[] phones = list.toArray(new Integer[list.size()]);
    pageContext.setAttribute("phones", phones);
    pageContext.setAttribute("photo", photo);
    int month = contact.getBirthday() == null ? 1 : contact.getBirthday().getMonthOfYear();
    pageContext.setAttribute("choice", month);
%>
<input id="photoButton" type="image" onclick="addPhoto()" src =<%= photo == null ? "img/incognito.gif" : photo%> />
 <form action="FrontController" method = "post" name="adding" onsubmit="return saveChanges(phones)">
     <input type="hidden" name="command" value="SaveContact">
     <div class="main">
   <div class="field"><label for="name"> Имя*</label> <input name ="name" id="name" required = "true" value=<%=contact.getName()%>></div>
   <div class="field"><label for="surname">Фамилия* </label> <input name ="surname" id="surname" required = "true" value=<%=contact.getSurname()%>></div>
   <div class="field"><label for="fathername"> Отчество </label><input name ="fathername" id="fathername" value=<%=StringUtils.isNullOrEmpty(contact.getParentName()) ? contact.getParentName() : ""%>></div>
    <label for="day">Дата рождения <input type ="text" name ="day" id="day" value=<%=contact.getBirthday() == null ? "" : contact.getBirthday().getDayOfMonth()%>> </label>
       <tr>
           <td>
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
           </td>
           </tr>
   </select>
       <input type ="text" name ="year" id ="year" value=<%=contact.getBirthday() == null ? "" : contact.getBirthday().getYear()%>>
   </div>

         <p> Пол<input type = "radio" name ="gender" id ="gender" value="female">женский<input type = "radio" name ="gender" value="male">мужской</p>
         <p> Гражданство<input name ="citizenship" id="citizenship" value=<%=contact.getCitizenship() == null? "" : contact.getCitizenship()%>></p>
     <div class="main">
         <div class="field"><label for="family"> Семейное положение </label><input name ="family" id="family"></div>
         <div class="field"><label for="webSite"> Web Site </label><input name ="webSite" id="webSite" value=<%=contact.getWebSite() == null? "" : contact.getWebSite()%>></div>
     </div>
         <p>Email <input name ="email" id="email" value=<%=contact.getEmail() == null ? "" : contact.getEmail() %>></p>
   <p>Текущее место работы <input name ="job" id="job" value=<%=contact.getJob()== null ? "" : contact.getJob()%>></p>
   <br>Адрес
   <p>Страна <input name ="country" id="country" value=<%=contact.getAddress() == null? "" : contact.getAddress().getCountry()%>></p>
   <p>Город <input name ="town" id="town" value=<%=contact.getAddress() == null? "" : contact.getAddress().getTown()%>></p>
   <p>Улица <input name ="street" id="street" value=<%=contact.getAddress() == null? "" : contact.getAddress().getStreet()%>></p>
   <p>Дом <input name ="house" id="house" value=<%=contact.getAddress() == null? "" : contact.getAddress().getHouse()%>></p>
   <p>Квартира <input name ="place" id="place" value=<%=contact.getAddress() == null? "" : contact.getAddress().getPlace()%>></p>
   <p>Индекс <input name ="postIndex" id="postIndex" value=<%=contact.getAddress() == null? "" : contact.getAddress().getPostIndex()%>></p>

     <input type="hidden" name="phones" id="phones" value=""/>
     <input type="hidden" name="id" id="id" value="<%=contactId%>"/>
     <input type="hidden" name="types" id="types" value=""/>
     <input type="hidden" name="comments" id="comments" value=""/>
     <input type="hidden" name="ids" id="ids" value=""/>
     <input type="hidden" name="phonesO" id="phonesO" value=""/>
     <input type="hidden" name="typesO" id="typesO" value=""/>
     <input type="hidden" name="commentsO" id="commentsO" value=""/>
     <input type="hidden" name="photo" id="photo" value="${photo}"/>
     <input type="submit" value="<%=buttonText%>">

 </form>
<input type="hidden" id="last" value="<%=contact.getPhoneNumbers().size()%>"/>
<tbl:phones id="<%=contactId == null ? 0: contactId%>"></tbl:phones>
<button value = "button" onclick="openPhone()">Добавить Номер</button>
<button value = "button" onclick="openAttachment()">Присоединить файл</button>
<button value = "button" onclick="removeSelected()">Удалить выбранные</button>

</body>
</html>