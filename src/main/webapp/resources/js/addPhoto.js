/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 06.03.15
 * Time: 15:33
 * To change this template use File | Settings | File Templates.
 */
function addPhoto() {
    window.document.getElementById("id").value = window.opener.document.getElementById("id").value;
    window.document.submit();
    window.close();
}