/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 06.03.15
 * Time: 15:19
 * To change this template use File | Settings | File Templates.
 */
function addPhoto()
{
    window.location.href  = 'addPhoto.jsp?id=' + window.document.getElementById("id").value;
}
function saveChanges() {
    if (document.getElementById("name").value.length == 0) {
        alert ('Пожалуйста, укажите имя.');
        return;
    }
    if (document.getElementById("surname").value.length == 0) {
        alert ('Пожалуйста, укажите Фамилию.');
        return;
    }
    var valid = validate();

    if (document.getElementById("id").value == 0) {
        document.forms.namedItem("adding").submit();
        return;
    }
    var rw;
    var str;
    var elem;
    var tbl = document.getElementById("tablePhones");
    for (rw = 0; rw< tbl.childNodes.length; ++rw) {
        str = tbl.childNodes[rw].id;
        if (str.substring(0, 8) == "phoneOld") {
            document.getElementById("ids").value += str.substring(8, str.length) + '#\t';
            continue;
        }
              elem = tbl.childNodes[rw].childNodes[1].childNodes[0];
              document.getElementById("numbers").value += elem.innerHTML + '#\t';
        elem = tbl.childNodes[rw].childNodes[2];
        document.getElementById("types").value += elem.innerHTML + '#\t';
        elem = tbl.childNodes[rw].childNodes[3];
        document.getElementById("comments").value += elem.innerHTML + '#\t';

    }

    tbl = document.getElementById("tableAtt");
    for (rw = 0; rw< tbl.childNodes.length; ++rw) {
        str = tbl.childNodes[rw].id;
        if (str.substring(0, 6) == "attOld") {
            document.getElementById("idsAtt").value += str.substring(6, str.length) + '#\t';
            continue;
        }
        var elem = tbl.childNodes[rw].childNodes[1].childNodes[0];
        document.getElementById("namesAtt").value += elem.innerHTML + '#\t';
        elem = tbl.childNodes[rw].childNodes[2];
        document.getElementById("datesAtt").value += elem.innerHTML + '#\t';
        elem = tbl.childNodes[rw].childNodes[3];
        document.getElementById("commentsAtt").value += elem.innerHTML + '#\t';

    }
    var valid = validate();
        document.forms.namedItem("adding").submit();
}

function openPhone() {
       document.getElementById('addPhone').style.display = "block";
}
function openAtt() {
    document.getElementById('addAtt').style.display = "block";
}
function hidePhone() {
    document.getElementById('phoneId').id=0;
        document.getElementById('addPhone').style.display = "none";
}
function hideAtt() {
    document.getElementById('attId').id=0;
    document.getElementById('addAtt').style.display = "none";
}
function removeSelected() {
    var elem = document.getElementsByName("newPhone");
    for(var i = 0; i < elem.length; i++)
    {
        if(elem[i].checked) {
            document.getElementById("tablePhones").removeChild(elem[i].parentNode.parentNode);
            --i;
        }
    }
    var elem2 = document.getElementsByName("oldPhone");
    for(var j = 0; j < elem2.length; j++)
    {
        if(elem2[j].checked) {
           // var id = elem2[j].value;
            document.getElementById("tablePhones").removeChild(elem2[j].parentNode.parentNode);
            --i;
        }
    }
}
function removeSelectedAtt() {
    var elem = document.getElementsByName("newAtt");
    for(var i = 0; i < elem.length; i++)
    {
        if(elem[i].checked) {
            document.getElementById("tableAtt").removeChild(elem[i].parentNode.parentNode);
            --i;
        }
    }
    var elem2 = document.getElementsByName("oldAtt");
    for(var j = 0; j < elem2.length; j++)
    {
        if(elem2[j].checked) {
            var id = elem2[j].value;
            document.getElementById("tableAtt").removeChild(elem2[j].parentNode.parentNode);
            --j;
        }
    }
}
function validate()
{
                var day = document.getElementById("day").value;
                var year = document.getElementById("year").value;
                var webSite = document.getElementById("webSite").value;
                var house = document.getElementById("house").value;
                var place = document.getElementById("place").value;
                var email = document.getElementById("email").value;
                var postIndex = document.getElementById("postIndex").value;

                var mailPattern = /[0-9a-z_.-]+@[0-9a-z_.-]+/i; //www.site-do.ru/js/js14.php
                if(isNaN(day) || isNaN(year)) {
                alert("Введите корректную дату!");
                return false;
                }
                if(isNaN(house) || isNaN(place) || isNaN(postIndex)) {
                alert("Введите корректный адрес!");
                return false;
                }
                if(email.length>0 && !mailPattern.test(email)) {
                    alert("Введите корректный email!");
                    return false;
                }
                if((day.length>0 && year.length==0) ||(day.length==0 && year.length>0)  ) {
                     alert("Введите корректую дату!");
                        return false;
                 }
    return true;
        }


function addNewPhone() {
    var valid = validatePhone();
    if (!valid) {
        return;
    }
    var phoneId = document.getElementById('phoneId').value;
    var number = document.getElementById("phone_country").value + '\t' + document.getElementById("operator").value +  '\t' +
        document.getElementById("number").value;
    var comment = document.getElementById("phone_comment").value;
    var type =  document.getElementById("cell").checked ? "CELL" : "HOME";
    var div2 = document.createElement('div');
    var divB = document.createElement('div');
    var checkBox = document.createElement('input');
    var divN = document.createElement('div');
    var btn =  document.createElement('button');
    btn.innerHTML=number;
    btn.type="button";
    btn.setAttribute("class", "ref");
    btn.setAttribute("onclick","editPhone(this)");
    divN.appendChild(btn);
    var divT = document.createElement('div');
    divT.innerHTML = type;
    var divC = document.createElement('div');
    divC.innerHTML = comment;
    checkBox.type = "checkBox";
    checkBox.name = "newPhone";
    div2.setAttribute("class", "row color0");
    divC.setAttribute("class", "col c20");
    divN.setAttribute("class", "col c20");
    divT.setAttribute("class", "col c20");
    divB.setAttribute("class", "col c5");

    divB.appendChild(checkBox);
    div2.appendChild(divB);
    div2.appendChild(divN);
    div2.appendChild(divT);
    div2.appendChild(divC);

    var last = document.getElementById("lastNew").value;
    document.getElementById("lastNew").value = last+1;
    div2.id = last+1;
    if (phoneId != 0) {
        document.getElementById("tablePhones").replaceChild(div2, document.getElementById(phoneId));
    }  else {
        document.getElementById("tablePhones").appendChild(div2);
    }
    document.getElementById('addPhone').style.display = "none";
}
function validatePhone()
{
    var country = document.getElementById("phone_country").value;
    var operator = document.getElementById("operator").value;
    var number = document.getElementById("number").value;
    if(isNaN(country) || isNaN(operator)|| isNaN(number)) {
        alert("Номер может содержать только цифры.");
        return false;
    }    else {
        return true;
    }
}
function editPhone(btn) {
    if (btn == null) {
        document.getElementById("addPhoneForm").reset();
        document.getElementById('phoneId').value = 0;
        openPhone();
        return;
    }
    document.getElementById('phoneId').value = btn.parentNode.parentNode.id;
    var phone = btn.parentNode.parentNode.childNodes[1].childNodes[0].innerHTML;
    var type = btn.parentNode.parentNode.childNodes[2].innerHTML;
    var comment = btn.parentNode.parentNode.childNodes[3].innerHTML;
    var ph = phone.split("\t");
    document.getElementById('phone_country').value = ph[0];
    document.getElementById('operator').value = ph[1];
    document.getElementById('number').value = ph[2];
    document.getElementById('phone_comment').value = comment;
    if (type == 'CELL') {
        document.getElementById('cell').value = 'checked';
    } else {
        document.getElementById('home').value = 'checked';
    }
    openPhone();
}
function editAtt(btn) {
    if (btn == null) {
        document.getElementById('uploadFile').style.hidden = true;
        document.getElementById("addAttForm").reset();
        document.getElementById('attId').value = 0;
        openAtt();
        return;
    }
    document.getElementById('uploadFile').style.hidden = false;
    document.getElementById('attId').value = btn.parentNode.parentNode.id;
    var name = btn.parentNode.parentNode.childNodes[1].childNodes[0].innerHTML;
    var comment = btn.parentNode.parentNode.childNodes[3].innerHTML;
    document.getElementById('fileName').value = name;
    document.getElementById('commentAtt').value = comment;
    openAtt();
}
function addNewAtt() {
    document.getElementById('uploadFile').style.hidden = false;
    var attId = document.getElementById('attId').value;
    var name = document.getElementById("fileName").value;
    var comment = document.getElementById("commentAtt").value;
    var date =  (new Date()).toDateString();
    var div2 = document.createElement('div');
    var divB = document.createElement('div');
    var checkBox = document.createElement('input');
    var divN = document.createElement('div');
    var btn =  document.createElement('button');
    btn.innerHTML=name;
    btn.type="button";
    btn.setAttribute("class", "ref");
    btn.setAttribute("onclick","editAtt(this)");
    divN.appendChild(btn);
    var divT = document.createElement('div');
    divT.innerHTML = date;
    var divC = document.createElement('div');
    divC.innerHTML = comment;
    checkBox.type = "checkBox";
    checkBox.name = "newAtt";
    div2.setAttribute("class", "row color0");
    divC.setAttribute("class", "col c20");
    divN.setAttribute("class", "col c20");
    divT.setAttribute("class", "col c20");
    divB.setAttribute("class", "col c5");

    divB.appendChild(checkBox);
    div2.appendChild(divB);
    div2.appendChild(divN);
    div2.appendChild(divT);
    div2.appendChild(divC);

    var last = document.getElementById("lastNewAtt").value;
    document.getElementById("lastNewAtt").value = last+1;
    div2.id = last+1;
    if (attId != 0) {
        document.getElementById("tableAtt").replaceChild(div2, document.getElementById(attId));
    }  else {
        document.getElementById("tableAtt").appendChild(div2);
    }
    document.forms.namedItem('addAttForm').submit();
    document.getElementById('addAtt').style.display = "none";
}