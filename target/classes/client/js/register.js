function pageLoad() {
    document.getElementById("registerButton").addEventListener("click", register);
}

function register(event) {
    event.preventDefault();

    const form = document.getElementById("registerForm");
    const formData = new FormData(form);

    fetch("/user/create", {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {

        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            document.getElementById("content").innerHTML = '<h1 id="successful">User successfully created!</h1>';
            setTimeout(function (){
                window.location.href = "login.html"
            }, 2000);

        }
});
}