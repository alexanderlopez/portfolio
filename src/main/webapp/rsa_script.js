
function initTool() {
    var link = document.getElementById('login_link');
    var welcome = document.getElementById('welcome');

    fetch('/rsa-data').then(response => response.json()).then(responseObject => {
        if (responseObject.loggedIn == true && responseObject.hasNickname == false) {
            window.location.replace('/nickname');
        }

        link.href = responseObject.link;
        link.innerText = responseObject.linkText;
        welcome.innerText = responseObject.welcome;

        if (responseObject.loggedIn == true) {
            document.getElementById("generate_key").disabled = false;

            if (responseObject.hasKey) {
                document.getElementById("private_exponent").innerText = responseObject.private_exponent;
                document.getElementById("public_modulus").innerText = responseObject.public_modulus;
                document.getElementById("public_exponent").innerText = responseObject.public_exponent;
            }
        }
    });
}

function transformCipher() {
    var parameters = '';
    var modulus = document.getElementById('modulus_text').value;
    var exponent = document.getElementById('exponent_text').value;
    var cipher = document.getElementById('cipher_text').value;

    parameters = parameters.concat('action=TRANSFORM')
                           .concat('&exponent=', exponent)
                           .concat('&modulus=', modulus)
                           .concat('&cipher=', cipher);

    var options = { method: 'POST',
                    headers: { 'Content-type': 'application/x-www-form-urlencoded'},
                    body: parameters};

    fetch('/rsa-data', options).then(response => response.text()).then(result_text => {
        document.getElementById('result_text').innerText = result_text;
    });
}

function generateKey() {
    var options = { method: 'POST',
                    headers: { 'Content-type': 'application/x-www-form-urlencoded'},
                    body: "action=GENERATE_KEY"};
    
    fetch('/rsa-data', options).then(response => response.json()).then(object => {
        document.getElementById('public_modulus').innerText = object.public_modulus;
        document.getElementById('public_exponent').innerText = object.public_exponent;
        document.getElementById('private_exponent').innerText = object.private_exponent;
    });
}