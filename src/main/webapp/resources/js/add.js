/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 06.03.15
 * Time: 15:19
 * To change this template use File | Settings | File Templates.
 */
function addPhoto()
{
    //  alert('addPhoto');
    window.location.href  = 'addPhoto.jsp?id=' + window.document.getElementById("id").value;
}
function saveChanges(phones) {

    for (var j in phones) {    //optimize it!
        //    alert(j);
        if (document.getElementById("oldN" + j) == null) {
            continue;
        }

        document.getElementById("ids").value += j + '#\t';
        var elem = document.getElementById("oldN" + j);
        document.getElementById("phonesO").value += elem.innerHTML + '#\t';
//
        elem = document.getElementById("oldT"+j);
//
        document.getElementById("typesO").value += elem.innerHTML + '#\t';
        elem = document.getElementById("oldC"+j);
        document.getElementById("commentsO").value += elem.innerHTML + '#\t';
    }

      alert('still here');
    for (var i = 0; i<=parseInt(document.getElementById("last").value); ++i) {

        if (document.getElementById("divN" + i) == null) {
            continue;
        }
        ///    alert(i);
        elem = document.getElementById("divN" + i);
        document.getElementById("phones").value += elem.innerHTML + '#\t';
//
        elem = document.getElementById("divT"+i);
//
        document.getElementById("types").value += elem.innerHTML + '#\t';
        elem = document.getElementById("divC"+i);
        document.getElementById("comments").value += elem.innerHTML + '#\t';
    }
    var valid = validate();
    alert(valid);

    if (!valid) {
        alert('validation didn\'t pass');
    } else {
        alert(valid);
        alert(valid);
      //  document.forms.namedItem("adding").submit();
    }

    return valid;
}

function openPhone() {
    window.open('phone.jsp',null,
        'height=200,width=400,status=yes,toolbar=no,menubar=no,location=no');
}
function openAttachment() {
    window.open('attachment.jsp?id=' + window.document.getElementById("id").value, null,
        'height=200,width=400,status=yes,toolbar=no,menubar=no,location=no');
}

function removeSelected() {
    var elem = document.getElementsByName("newPhone");
    for(var i = 0; i < elem.length; i++)
    {
        if(elem[i].checked) {
            document.body.removeChild(elem[i].parentNode);
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

                var mailPattern = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

                if(isNaN(day) || isNaN(year)) {
                alert("Введите корректную дату!");
                return false;
                }
                if(isNaN(house) || isNaN(place) || isNan(postIndex)) {
                alert("Введите корректный адрес!");
                return false;
                }
                if(email.length>0 && !mailPattern.test(email)) {
                    alert("Введите корректный email!");
                    return false;
                }
                if(day.length>0 && year.length==0 ||day.length==0 && year.length>0  ) {
                     alert("Введите корректный адрес!");
                        return false;
                 }
    return true;
        }

