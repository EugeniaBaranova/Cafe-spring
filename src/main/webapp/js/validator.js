function validateForm() {

    var currentLanguage = document.getElementById('reg_form').getAttribute('data-language');

    var nameField = document.forms["registration_form"]["name"].value;
    var loginField = document.forms["registration_form"]["login"].value;
    var emailField = document.forms["registration_form"]["email"].value;
    var passwordField = document.forms["registration_form"]["password"].value;

    var nameRegExp = /^[a-zA-Zа-яА-Я ]{5,30}$/;
    var loginRegExp = /^[a-zA-Zа-яА-Я0-9 ]{5,30}$/;
    var emailRegExp = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    var passwordRegExp = /^(((?=.*[a-z])(?=.*[A-Z]))|((?=.*[a-z])(?=.*[0-9]))|((?=.*[A-Z])(?=.*[0-9])))(?=.{6,})/;

    var countOfErrors = 0;

    if(!nameRegExp.test(nameField)){
        document.getElementById('name_error').innerHTML = "* name should have size not lower then 5 symbols and \n" +
            "contain only letters";
        countOfErrors = countOfErrors + 1;
    }else {
        document.getElementById('name_error').innerHTML = "";
    }

    if (!loginRegExp.test(loginField)) {
        document.getElementById('login_error').innerHTML = "* login should have size not lower then 5 symbols and \n" +
            "contain letters or/and numbers ";
        countOfErrors = countOfErrors + 1;
    } else {
        document.getElementById('login_error').innerHTML = "";
    }

    if(!emailRegExp.test(emailField)){
        document.getElementById('email_error').innerHTML = "* wrong email";
        countOfErrors = countOfErrors + 1;
    }else {
        document.getElementById('email_error').innerHTML = "";
    }

    if(!passwordRegExp.test(passwordField) || passwordField.length < 6){
        document.getElementById('password_error').innerHTML = "* password must have at least six symbols,\n" +
            "at least one number and at least one special character ";
        countOfErrors = countOfErrors + 1;
    }else {
        document.getElementById('password_error').innerHTML = "";
    }

    return countOfErrors == 0;
}
