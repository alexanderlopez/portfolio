
function initTool() {
    var link = document.getElementById('login_link');
    var welcome = document.getElementById('welcome');

    fetch('/rsa-data').then(response => response.json()).then(responseObject => {
        console.log(responseObject);
        if (responseObject.loggedIn == true && responseObject.hasNickname == false) {
            window.location.replace('/nickname');
        }

        link.href = responseObject.link;
        link.innerText = responseObject.linkText;
        welcome.innerText = responseObject.welcome;
    });
}