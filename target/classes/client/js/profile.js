function pageLoad() {

        let profileHTML ="";
        fetch("/user/list", {method: 'get'}
        ).then(response => response.json()
        ).then(responseData => {

            if (responseData.hasOwnProperty('error')) {
                alert(responseData.error);
            } else {
                for(let profile of responseData)
                profileHTML = `<label class="userInfo">Name: ${profile.FirstName}</label><br>` +
                    `<label class="userInfo">Surname ${profile.LastName}</label><br>` +
                    `<label class="userInfo">Gender: ${profile.Gender}</label><br>` +
                    `<label class="userInfo">Username: ${profile.Username}</label><br>` +
                    `<label class="userInfo">Password: ${profile.Password}</label><br>` +
                    `<label class="userInfo">Email: ${profile.Email}</label><br>` +
                    `<label class="userInfo">Desired Weight: ${profile.IdealWeight}</label>`;
                document.getElementById("userInfo").innerHTML = profileHTML;


            }
        });

        document.getElementById("logoutButton").addEventListener("click", logout);
}

function logout() {

    fetch("/user/logout", {method: 'post'}
    ).then(response => response.json()
    ).then(responseData => {
        if (responseData.hasOwnProperty('error')) {

            alert(responseData.error);

        } else {

            window.location.href = '/client/index.html';

        }
    });

}
