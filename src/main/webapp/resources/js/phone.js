/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 06.03.15
 * Time: 15:35
 * To change this template use File | Settings | File Templates.
 */
function addNewPhone() {
   // alert('addNewPhone');
    var id =  parseInt(window.opener.document.getElementById("last").value);
    window.opener.document.getElementById("last").value=id+1;
   // alert(document.getElementById("country").value);
    var number = document.getElementById("country").value + '(' + document.getElementById("operator").value +  ')' +
        document.getElementById("number").value;
  //  alert(number);
    var comment = document.getElementById("comment").value;
    var type =  document.getElementById("cell").checked ? "CELL" : "HOME";
    var div2 = window.opener.document.createElement('div');
    var checkBox = window.opener.document.createElement('input');
    var divN = window.opener.document.createElement('span');
    divN.innerHTML = number;
    divN.id = "divN" + id;
    var divT = window.opener.document.createElement('span');
    divT.innerHTML = type;
    divT.id = "divT" + id;
    var divC = window.opener.document.createElement('span');
    divC.innerHTML = comment;
    divC.id = "divC" + id;
    checkBox.type = "checkBox";
    //  checkBox.setName("newPhone");
    checkBox.name = "newPhone";
    // div2.innerHTML = number + "\t"  + type + "\t" + comment;
    div2.appendChild(checkBox);
    div2.appendChild(divN);
    div2.appendChild(divT);
    div2.appendChild(divC);
    window.opener.document.body.appendChild(div2);
    window.close();
}