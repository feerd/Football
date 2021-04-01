/**
 * view-controller for playeredit.html
 *
 * M133: Football
 *
 * @author  David Feer
 */

/**
 * register listeners and load the player data
 */
$(document).ready(function () {
    loadTeams();
    loadPlayer();

    /**
     * listener for submitting the form
     */
    $("#playereditForm").submit(savePlayer);

    /**
     * listener for button [abbrechen], redirects to football
     */
    $("#cancel").click(function () {
        window.location.href = "../football.html";
    });


});

/**
 *  loads the data of this player
 *
 */
function loadPlayer() {
    let playerUUID = $.urlParam("uuid");
    if (playerUUID) {
        $
            .ajax({
                url: "./resource/player/read?uuid=" + playerUUID,
                dataType: "json",
                type: "GET"
            })
            .done(showPlayer)
            .fail(function (xhr, status, errorThrown) {
                if (xhr.status === 403) {
                    window.location.href = "./login.html";
                } else if (xhr.status == 404) {
                    $("#message").text("Kein Buch gefunden");
                } else {
                    window.location.href = "../football.html";
                }
            })
    }

}

/**
 * shows the data of this player
 * @param  player  the player data to be shown
 */
function showPlayer(player) {
    $("#playerUUID").val(player.playerUUID);
    $("#playerName").val(player.playerName);
    $("#team").val(player.team.teamUUID);

}

/**
 * sends the player data to the webservice
 * @param form the form being submitted
 */
function savePlayer(form) {
    form.preventDefault();

    let url = "./resource/player/";
    let type;

    let playerUUID = $("#playerUUID").val();
    if (playerUUID) {
        type= "PUT";
        url += "update"
    } else {
        type = "POST";
        url += "create";
    }

    $
        .ajax({
            url: url,
            dataType: "text",
            type: type,
            data: $("#playereditForm").serialize()
        })
        .done(function (jsonData) {
            window.location.href = "../football.html"
        })
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status === 404) {
                $("#message").text("Dieser Spieler existiert nicht");
            } else {
                $("#message").text("Fehler beim Speichern des Spielers");
            }
        })
}

function loadTeams() {
    $
        .ajax({
            url: "./resource/team/list",
            dataType: "json",
            type: "GET"
        })
        .done(showTeams)
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status === 404) {
                $("#message").text("Kein Team gefunden");
            } else {
                window.location.href = "../football.html";
            }
        })
}

function showTeams(teams) {

    $.each(teams, function (uuid, team) {
        $('#team').append($('<option>', {
            value: team.teamUUID,
            text: team.team
        }));
    });
}
