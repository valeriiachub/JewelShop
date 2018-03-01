var passwordPattern = /^[A-Za-z]\w{7,14}$/;
var emailPattern = /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/;
var namePattern = /^[a-zA-Z]+$/;

$(document).ready(function () {
    $('#submit').click(function () {
        if(!validateFirstName()){
            alert('You have entered an invalid first name!');
        }
        if(!validateLastName()){
            alert('You have entered an invalid last name!');
        }
        if (!validateEmail()) {
            alert('You have entered an invalid email address!');
        }
        if (!validatePassword()) {
            alert('You have entered an invalid password!');
        }
    });
});


function validateFirstName(){
    var firstName = $('#first_name').val();
    return namePattern.test(firstName);
}

function validateLastName(){
    var lastName = $('#last_name').val();
    return namePattern.test(lastName);
}

function validatePassword() {
    var password = $('#password').val();
    return passwordPattern.test(password);
}

function validateEmail() {
    var email = $('#email').val();
    return emailPattern.test(email);
}