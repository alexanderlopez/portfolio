
function initTool() {
    var link = document.getElementById('login_link');
    var welcome = document.getElementById('welcome');

    fetch('/rsa-data').then(response => response.json()).then(object => {
        link.href = object.link;
        link.innerText = object.linkText;
        welcome.innerText = object.welcome;
    });
}