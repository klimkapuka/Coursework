function pageLoad() {

    fetch("/user/list", {method: 'get'}
    ).then(response => response.json()
    ).then(responseData => {

        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            document.getElementById("userInfo").innerHTML = `<label id="name">reponseData.FirstName</label><br>
        <label id="surname"></label><br>
        <label id="gender"></label><br>
        <label id="username"></label><br>
        <label id="password"></label><br>
        <label id="email"></label><br>
        <label id="idealWeight"></label>`;
            }
    });

}