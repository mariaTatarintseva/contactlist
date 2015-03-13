/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 13.03.15
 * Time: 11:31
 * To change this template use File | Settings | File Templates.
 */
  function updateText() {
    var text = document.getElementById("message").value;
    document.getElementById("view").innerHTML = text + " modified";
    document.getElementById("arg2").value = text + " modified";
}