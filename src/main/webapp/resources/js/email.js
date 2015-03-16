/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 13.03.15
 * Time: 11:31
 * To change this template use File | Settings | File Templates.
 */
  function updateText() {
    var text = document.getElementById("mailTxt").value;
    var starts = ["", "Здравствуйте, Иван!\n", "", "Здравствуйте, Иван!\n"];
    var ends = ["", "\nС уважением, \nМария", "\nС уважением, Мария\n1 Jan 2015", "\nС уважением, Мария\n1 Jan 2015"];
    var choice = document.getElementById('templates').value;
    document.getElementById("view").innerHTML = starts[choice].concat(text, ends[choice]);

    // alert(text);
//    document.getElementById("view").innerHTML = text + " modified";
  //  document.getElementById("message").value = text;
}
function sendEmail() {
   // alert('sending');
   // alert(document.getElementById("mailTxt").value);
  //  alert('sending');
  // document.getElementById("message").value=document.getElementById("mailTxt").value;
   // alert('sending');
    //document.forms.namedItem("emailForm").submit();
}