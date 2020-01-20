function pageLoad() {

    let modal = document.getElementById('modal1');
    window.onclick = function (event) {
        if  (event.target === modal) {
            modal.style.display = "none";
        }
    }

}

function checkLogin() {

    let username = Cookies.get("username");

    if (username === undefined) {
        return false;
    } else {
        return true;
    }
}



