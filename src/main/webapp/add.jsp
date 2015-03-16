<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tbl" uri="contacts" %>
<%@ page import="dataclasses.Contact" %>
<%@ page import="dao.DataAccessObject" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.mysql.jdbc.StringUtils" %>
<%@ page import="commandclasses.FileUpload" %>
<%@ page import="java.io.File" %>
<%@ page import="org.apache.commons.io.FileUtils" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
   <link rel="stylesheet" type="text/css" href="style.css">
    <script src="<c:url value="/resources/js/add.js" />"></script>
<title>Редактировать контакт</title>
</head>
<body>


<%
    Contact contact;
    String buttonText = "Сохранить";
    Integer contactId = null;
    if (request.getParameter("id") == null) {
        contact = new Contact();
        contact.setName("");
        contact.setSurname("");
        contactId=0;
   //     phoneNumbers = new ArrayList<PhoneNumber>();
        buttonText = "Добавить контакт";
    }    else {
        contact = DataAccessObject.getFromDatabase(Integer.valueOf(request.getParameter("id")));
        contactId = Integer.valueOf(request.getParameter("id"));
    }
    String photo=null;
    if (!StringUtils.isNullOrEmpty((String)request.getSession().getAttribute("photo"))) {
        photo = (String) request.getSession().getAttribute("photo");
        request.getSession().removeAttribute("photo");
        contact.setPhoto(photo);
    } else if (contactId != null) {
        photo = contact.getPhoto();
    }
    System.out.print("Photo: " + photo);
    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    pageContext.setAttribute("months", months);

    String[] family = {"NOT SPECIFIED", "DIVORCED", "DATING", "MARRIED", "SINGLE", "WIDOW"};
    pageContext.setAttribute("family", family);


    ArrayList<Integer> list = DataAccessObject.getPhonesOf(contactId);
    pageContext.setAttribute("photo", photo);
    int month = contact.getBirthday() == null ? 1 : contact.getBirthday().getMonthOfYear();
    pageContext.setAttribute("choice", month);

    int fam = contact.getFamilyStatus() == null ? 0: contact.getFamilyStatus().ordinal();
    pageContext.setAttribute("fam", fam);

    FileUpload.initProperties(request.getServletContext());

    if (!StringUtils.isNullOrEmpty(photo)) {
    String photoFrom = String.format("%s%s%s", FileUpload.UPLOAD_DIRECTORY, File.separator, photo);
        String[] path = photo.split("/");

        String photoTo = String.format("%s/resources/avatars/%s", request.getServletContext().getRealPath(""),path[1] );
        FileUtils.copyFileToDirectory(new File(photoFrom), new File(photoTo));
    }

%>
<div id="header">
    <h1>Редактирование контакта</h1>
</div>
<div id="container">
<div id = "nav">
    <button type="button" class="ref" onclick="editPhone(null)">Добавить Номер</button>
    <button type="button" class="ref" onclick="editAtt(null)">Присоединить файл</button>
</div>
<div id="content">
<input id="photoButton" type="image" style="width:25%;" onclick="addPhoto()" src =<%= photo == null ? "resources/avatars/incognito.gif" : "resources/" + photo%> />
 <form action="FrontController" method = "post" name="adding" accept-charset="utf-8">
     <input type="hidden" name="command" value="SaveContact">
     <div class="main">
   <div class="field"><label for="name"> Имя*</label> <input name ="name" id="name" required = "true" maxlength="20" value=<%=contact.getName()%> ></div>
   <div class="field"><label for="surname">Фамилия* </label> <input name ="surname" id="surname" required = "true" maxlength="30" value=<%=contact.getSurname()%>></div>
   <div class="field"><label for="fathername"> Отчество </label><input name ="fathername" id="fathername" maxlength="30" value=<%=StringUtils.isNullOrEmpty(contact.getParentName()) ? "" : contact.getParentName()%>></div>
         <div class="field"><label>Дата рождения </label></div><div><input type ="text" name ="day" id="day" size="2" value=<%=contact.getBirthday() == null ? "" : contact.getBirthday().getDayOfMonth()%>> </div>

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
       <input type ="text" name ="year" id ="year" size="4" value=<%=contact.getBirthday() == null ? "" : contact.getBirthday().getYear()%>>

          <%if (contact.getGender() == Contact.Gender.FEMALE) {%>
         <div class="field"><label for="gender"> Пол</label><input type = "radio" name ="gender" value="FEMALE" checked>женский<input type = "radio" name ="gender" value="MALE">мужской</div>

         <%} else {%>
         <div class="field"><label for="gender"> Пол</label><input type = "radio" name ="gender" id ="gender" value="FEMALE">женский<input type = "radio" name ="gender" value="MALE" checked>мужской</div>

         <%}%>
         <div class="field"><label for="citizenship"> Гражданство</label><input type="text" name ="citizenship" maxlength="30" id="citizenship" value=<%=contact.getCitizenship() == null? "" : contact.getCitizenship()%>></div>

         <div class="field"><label for="family"> Семейное положение </label>
             <select name="family" id ="family" size="1">
                 <option value=""></option>
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
         <div class="field"><label for="webSite"> Web Site </label><input name ="webSite" id="webSite" maxlength="50" value=<%=contact.getWebSite() == null? "" : contact.getWebSite()%>></div>

     <div class="field"><label for="email"> Email </label><input name ="email" id="email" maxlength="50" value=<%=contact.getEmail() == null ? "" : contact.getEmail() %>></div>
     <div class="field"><label for="job" > Текущее место работы </label><input name ="job" maxlength="20" id="job" value=<%=contact.getJob()== null ? "" : contact.getJob()%>></div>
     <div class="field"><label>Адрес</label></div>
     <div class="field"><label for="country"> Страна </label><input name ="country"maxlength="20" id="country" value=<%=contact.getAddress() == null? "" : contact.getAddress().getCountry()%>></div>
     <div class="field"><label for="town"> Город </label><input name ="town" id="town" maxlength="20" value=<%=contact.getAddress() == null || StringUtils.isNullOrEmpty(contact.getAddress().getTown())? "" : contact.getAddress().getTown()%>></div>
     <div class="field"><label for="street"> Улица </label><input name ="street" id="street" maxlength="20" value=<%=contact.getAddress() == null|| StringUtils.isNullOrEmpty(contact.getAddress().getStreet())? "" : contact.getAddress().getStreet()%>></div>
     <div class="field"><label for="house">Дом</label> <input name ="house" id="house" maxlength="10" value=<%=contact.getAddress() == null || contact.getAddress().getHouse() == null? "" : contact.getAddress().getHouse()%>></div>
     <div class="field"><label for="place"> Квартира </label><input name ="place" id="place" maxlength="10" value=<%=contact.getAddress() == null|| contact.getAddress().getPlace() == null? "" : contact.getAddress().getPlace()%>></div>
     <div class="field"><label for="postIndex"> Индекс </label><input name ="postIndex" id="postIndex" maxlength="10" value=<%=contact.getAddress() == null|| contact.getAddress().getPostIndex() == null? "" : contact.getAddress().getPostIndex()%>></div>

     <input type="hidden" name="phones" id="phones" value=""/>
     <input type="hidden" name="id" id="id" value=<%=contactId%>>
     <input type="hidden" name="numbers" id="numbers" value=""/>
     <input type="hidden" name="types" id="types" value=""/>
     <input type="hidden" name="comments" id="comments" value=""/>
     <input type="hidden" name="ids" id="ids" value=""/>

         <input type="hidden" name="namesAtt" id="namesAtt" value=""/>
         <input type="hidden" name="datesAtt" id="datesAtt" value=""/>
         <input type="hidden" name="commentsAtt" id="commentsAtt" value=""/>
         <input type="hidden" name="idsAtt" id="idsAtt" value=""/>


         <input type="hidden" name="photo" id="photo" value="${photo}"/>
     <button type="button" class="ref" onclick="saveChanges()"><%=buttonText%></button>

     </div>
 </form>




</div>


</div>
<div> <h3>Список телефонов</h3></div>
<button type="button" class="btnClass" onclick="removeSelected()">Удалить выбранные телефоны</button>
<input type="hidden" id="lastAtt" value="<%=contact.getAttachments().size()%>"/>
<tbl:phones id="<%=contactId == null ? 0: contactId%>"></tbl:phones>

<button type="button" class="btnClass" onclick="removeSelectedAtt()">Удалить выбранные присоединения</button>
<div> <h3>Список присоединений</h3></div>
<tbl:attachments id="<%=contactId == null ? 0: contactId%>"></tbl:attachments>
<div id="footer">
    iTechArt 2015
</div>
<div id="addAtt">
    <!-- Popup Div Starts Here -->
    <div id="popupAtt">

        <form method = "post" name="addAttForm" action="FrontController" id="addAttForm" enctype="multipart/form-data" target="addAttReserve">
            Select file to upload:
            <input type="file" name="uploadFile" id="uploadFile"/>
            <input type="text" name="name" id="fileName" maxlength="20"/>
            <input type="text" name="comment" id="commentAtt" maxlength="20"/>
            <br/><br/>
            <input type="hidden" name="command" value="FileUpload">
            <input type="hidden" name="directory" value="attachments">
            <input type="hidden" name="id" id="id" value=<%=contactId%>>

            <button type="button"  onclick="addNewAtt()">Сохранить</button>
            <input type="hidden" id="attId" name="attId" value="0">
            <input type="hidden" id="lastNewAtt" name="lastNewAtt" value="0">
            <input type="submit" value="Отменить" id="cancelAtt" onclick="hideAtt()">
            <%--            <input type="submit" value="Upload">--%>
        </form>

    </div>
</div>
<iframe id="addAttReserve" style="display:none;" name="addAttReserve"></iframe>
<div id="addPhone">
<div class="main">
    <form method="POST" name="addPhoneForm" id="addPhoneForm" target="addPhoneReserve">

        <div class="field"><label for="phone_country">Код страны</label> <input id ="phone_country" maxlength="10"></div>
        <div class="field"><label for="operator">Код оператора</label> <input id ="operator" maxlength="10"></div>
        <div class="field"><label for="number">Телефонный номер </label><input id ="number" maxlength="50"> </div>
        <div class="field"><label for="home">Тип телефона</label> <input type = "radio" name="type" id ="home" checked="checked">Домашний<input name="type" type = "radio" id ="cell">Мобильный</div>
        <div class="field"><label for="phone_comment">Комментарий </label><input id ="phone_comment" maxlength="100"></div>
        <button type="button"  onclick="addNewPhone()">Сохранить</button>
        <input type="hidden" id="phoneId" name="phoneId" value="0">
        <input type="hidden" id="lastNew" name="lastNew" value="0">
        <input type="submit" value="Отменить" id="cancel" onclick="hidePhone()">
    </form>
</div>
</div>
<iframe id="addPhoneReserve" style="display:none;" name="addPhoneReserve" ></iframe>

</body>
</html>