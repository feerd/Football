/**
 * view-controller for login.html
 *
 * M133: Football
 *
 * @author  David Feer
 */

/**
 * register listeners
 */
$(document).ready(function () {

    /**
     * listener for submitting the form sends the login data to the web service
     */
    $("#loginForm").submit(sendLogin);
    /**
     * listener for button [Abmelden]
     */
    $("#logoff").click(sendLogoff);

});

/**
 * sends the login-request
 * @param form the form with the username/password
 */
function sendLogin(form) {
    form.preventDefault()
    $
        .ajax({
            url: "./resource/user/login",
            dataType: "text",
            type: "POST",
            data: $("#loginForm").serialize()
        })
        .done(function (jsonData) {
            window.location.href = "./football.html"
        })
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status === 404) {
                $("#message").text("Benutzername/Passwort unbekannt");
            } else {
                $("#message").text("Es ist ein Fehler aufgetreten");
            }
        })
}

/**
 * sends the logoff-request
 */
function sendLogoff() {
    $
        .ajax({
            url: "./resource/user/logout",
            dataType: "text",
            type: "DELETE",
        })
        .done(function (jsonData) {
            window.location.href = "./login.html"
        })
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status === 404) {
                $("#message").text("Benutzername/Passwort unbekannt");
            } else {
                $("#message").text("Es ist ein Fehler aufgetreten");
            }
        })
}
