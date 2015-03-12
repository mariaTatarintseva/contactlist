/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 06.03.15
 * Time: 15:35
 * To change this template use File | Settings | File Templates.
 */
function addNewPhone() {
    var valid = validate();
   // alert(valid);
    if (!valid) {
        return;
    }
    var id =  parseInt(window.opener.document.getElementById("last").value);
    window.opener.document.getElementById("last").value=id+1;
    var number = document.getElementById("country").value + '\t' + document.getElementById("operator").value +  '\t' +
        document.getElementById("number").value;
    var comment = document.getElementById("comment").value;
    var type =  document.getElementById("cell").checked ? "CELL" : "HOME";
    var div2 = window.opener.document.createElement('div');
    var checkBox = window.opener.document.createElement('input');
    var divN = window.opener.document.createElement('div');
    divN.innerHTML = number;
    divN.id = "divN" + id;
    var divT = window.opener.document.createElement('div');
    divT.innerHTML = type;
    divT.id = "divT" + id;
    var divC = window.opener.document.createElement('div');
    divC.innerHTML = comment;
    divC.id = "divC" + id;
    checkBox.type = "checkBox";
    checkBox.name = "newPhone";
    div2.setAttribute("class", "row");
    divC.setAttribute("class", "col c20");
    divN.setAttribute("class", "col c20");
    divT.setAttribute("class", "col c20");
    div2.appendChild(checkBox);
    div2.appendChild(divN);
    div2.appendChild(divT);
    div2.appendChild(divC);
    window.opener.document.getElementById("content").appendChild(div2);
    window.close();
}
function validate()
{
    var country = document.getElementById("country").value;
    var operator = document.getElementById("operator").value;
    var number = document.getElementById("number").value;
    if(isNaN(country) || isNaN(operator)|| isNaN(number)) {
        alert("Номер может содержать только цифры.");
        return false;
    }    else {
    return true;
    }
}