function pageLoad() {

    if (checkLogin() === false) {
        window.location.href = "index.html";
    } else {

        let username = Cookies.get("username");

        fetch("/user/list", {method: 'get', body: username}
        ).then(response => response.json()
        ).then(responseData => {

            if (responseData.hasOwnProperty('error')) {
                alert(responseData.error);
            } else {
                document.getElementById("userInfo").innerHTML = `<label id="name">${responseData.FirstName}</label><br>
        <label id="surname">${responseData.LastName}</label><br>
        <label id="gender">${responseData.Gender}</label><br>
        <label id="username">${responseData.Username}</label><br>
        <label id="password">${responseData.Password}</label><br>
        <label id="email">${responseData.Email}</label><br>
        <label id="idealWeight">${responseData.IdealWeight}</label>`;
            }
        });
    }
}
