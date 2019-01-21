function validateForm() {

    var costField = document.forms["addition_form"]["cost"].value;

    var currentLanguage = document.getElementById('reg_form').getAttribute('data-language');

    var costRegExp = /^[0-9]{1,2}.[0-9]{2}$/;
    var enUS = /en_US/;
    var en = /en/;

    var countOfErrors = 0;

    if (!costRegExp.test(costField)) {
        if (enUS.test(currentLanguage) || en.test(currentLanguage)) {
            document.getElementById('cost_error').innerHTML = "* cost should have next format - dd.dd";
        } else {
            document.getElementById('cost_error').innerHTML = "* цена должна быть следующего формата - dd.dd";
        }
        countOfErrors = countOfErrors + 1;
    } else {
        document.getElementById('cost_error').innerHTML = "";
    }

    return countOfErrors == 0;
}
