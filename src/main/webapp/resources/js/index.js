/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 06.03.15
 * Time: 15:10
 * To change this template use File | Settings | File Templates.
 */

function initializePages() {
    //       alert('initializePages')
    if (sessionStorage.getItem("page") == null) {
    sessionStorage.setItem("page", 0);
    sessionStorage.setItem("onPage", 10);
    }
}
    function removeSelected() {
        document.getElementById('com').value = 'RemoveContacts';
        document.submit();
    }
function emailToSelected() {
    document.getElementById('com').value = 'Email';
    document.submit();
}

