var emailPattern = /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/
var passPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$/;
var namePattern = /^[a-zA-Z]+$/;

function validateField(field, pattern){
    return field.value.match(pattern);
}

function validate(firstName, lastName, email, password){
    if (!validateField(firstName, namePattern)) {
        alert("You have entered an invalid first name!");
    }
    if (!validateField(lastName, namePattern)) {
        alert("You have entered an invalid last name!");
    }
    if (!validateField(email, emailPattern)) {
        alert("You have entered an invalid email address!");
    }
    if (!validateField(password, passPattern)) {
        alert("You have entered an invalid password!");
    }
}


