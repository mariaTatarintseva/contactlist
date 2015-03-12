<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tbl" uri="contacts" %>
<%@ page import="dataclasses.Contact" %>
<%@ page import="com.mysql.jdbc.StringUtils" %>
<%@ page import="dao.DataAccessObject" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@ page import="java.io.File" %>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@ page import="org.apache.commons.fileupload.FileItem" %>
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
    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    pageContext.setAttribute("months", months);

    String[] family = {"NOT SPECIFIED", "DIVORCED", "DATING", "MARRIED", "SINGLE", "WIDOW"};
    pageContext.setAttribute("family", family);


    ArrayList<Integer> list = DataAccessObject.getPhonesOf(contactId);
    Integer[] phones = list.toArray(new Integer[list.size()]);
    pageContext.setAttribute("phones", phones);
    pageContext.setAttribute("photo", photo);
    int month = contact.getBirthday() == null ? 1 : contact.getBirthday().getMonthOfYear();
    pageContext.setAttribute("choice", month);

    int fam = contact.getFamilyStatus() == null ? 0: contact.getFamilyStatus().ordinal();
    pageContext.setAttribute("fam", fam);

//    //upload photo
//    if (!StringUtils.isNullOrEmpty(photo)) {
//    DiskFileItemFactory factory = new DiskFileItemFactory();
//    factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
//    ServletFileUpload upload = new ServletFileUpload(factory);
//    FileItem fileItem = factory.createItem(null, null, false, photo);
//    String uploadPath =  request.getServletContext().getRealPath("") + File.separator + "photo";
//        File uploadDir = new File(uploadPath);
//        if (!uploadDir.exists()) {
//            uploadDir.mkdir();
//        }
//                File storeFile = new File(photo+File.separator + "1.gif");
//                fileItem.write(storeFile);
//    }


%>
<div id="header">
    <h1>Редактирование контакта</h1>
</div>
<div id="container">
<div id = "nav">
    <button type="button" class="ref" onclick="openPhone()">Добавить Номер</button>
    <button type="button" class="ref" onclick="openAttachment()">Присоединить файл</button>
</div>
<div id="content">
<input id="photoButton" type="image" onclick="addPhoto()" src =<%= photo == null ? "img/incognito.gif" : "photo"%> />
 <form action="FrontController" method = "post" name="adding" onsubmit="return saveChanges(phones)" accept-charset="utf-8">
     <input type="hidden" name="command" value="SaveContact">
     <div class="main">
   <div class="field"><label for="name"> Имя*</label> <input name ="name" id="name" required = "true" value=<%=contact.getName()%>></div>
   <div class="field"><label for="surname">Фамилия* </label> <input name ="surname" id="surname" required = "true" value=<%=contact.getSurname()%>></div>
   <div class="field"><label for="fathername"> Отчество </label><input name ="fathername" id="fathername" value=<%=StringUtils.isNullOrEmpty(contact.getParentName()) ? contact.getParentName() : ""%>></div>
         <div class="field"><label for="year">Дата рождения </label><input type ="text" name ="day" id="day" size="2" value=<%=contact.getBirthday() == null ? "" : contact.getBirthday().getDayOfMonth()%>> </div>

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
         <div class="field"><label for="citizenship"> Гражданство</label><input type="text" name ="citizenship" id="citizenship" value=<%=contact.getCitizenship() == null? "" : contact.getCitizenship()%>></div>

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
         <div class="field"><label for="webSite"> Web Site </label><input name ="webSite" id="webSite" value=<%=contact.getWebSite() == null? "" : contact.getWebSite()%>></div>

     <div class="field"><label for="email"> Email </label><input name ="email" id="email" value=<%=contact.getEmail() == null ? "" : contact.getEmail() %>></div>
     <div class="field"><label for="job"> Текущее место работы </label><input name ="job" id="job" value=<%=contact.getJob()== null ? "" : contact.getJob()%>></div>
     <div class="field"><label>Адрес</label></div>
     <div class="field"><label for="country"> Страна </label><input name ="country" id="country" value=<%=contact.getAddress() == null? "" : contact.getAddress().getCountry()%>></div>
     <div class="field"><label for="town"> Город </label><input name ="town" id="town" value=<%=contact.getAddress() == null || StringUtils.isNullOrEmpty(contact.getAddress().getTown())? "" : contact.getAddress().getTown()%>></div>
     <div class="field"><label for="street"> Улица </label><input name ="street" id="street" value=<%=contact.getAddress() == null|| StringUtils.isNullOrEmpty(contact.getAddress().getStreet())? "" : contact.getAddress().getStreet()%>></div>
     <div class="field"><label for="house">Дом</label> <input name ="house" id="house" value=<%=contact.getAddress() == null || contact.getAddress().getHouse() == null? "" : contact.getAddress().getHouse()%>></div>
     <div class="field"><label for="place"> Квартира </label><input name ="place" id="place" value=<%=contact.getAddress() == null|| contact.getAddress().getPlace() == null? "" : contact.getAddress().getPlace()%>></div>
     <div class="field"><label for="postIndex"> Индекс </label><input name ="postIndex" id="postIndex" value=<%=contact.getAddress() == null|| contact.getAddress().getPostIndex() == null? "" : contact.getAddress().getPostIndex()%>></div>

     <input type="hidden" name="phones" id="phones" value=""/>
     <input type="hidden" name="id" id="id" value=<%=contactId%>>
     <input type="hidden" name="types" id="types" value=""/>
     <input type="hidden" name="comments" id="comments" value=""/>
     <input type="hidden" name="ids" id="ids" value=""/>
     <input type="hidden" name="phonesO" id="phonesO" value=""/>
     <input type="hidden" name="typesO" id="typesO" value=""/>
     <input type="hidden" name="commentsO" id="commentsO" value=""/>
     <input type="hidden" name="photo" id="photo" value="${photo}"/>
     <input type="submit" value="<%=buttonText%>">
     <button type="button" onclick="removeSelected()">Удалить выбранные телефоны</button>
     </div>
 </form>


<input type="hidden" id="last" value="<%=contact.getPhoneNumbers().size()%>"/>
<tbl:phones id="<%=contactId == null ? 0: contactId%>"></tbl:phones>
 </div>
</div>
<div id="footer">
    iTechArt 2015
</div>

</body>
</html>