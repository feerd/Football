/**
 * view-controller for football.html
 *
 * M133: Football
 *
 * @author  David Feer
 */

/**
 * register listeners and load all players
 */

$(document).ready(function () {
    loadPlayers();

    /**
     * listener for buttons within shelfForm
     */

    $("#shelfForm").on("click", "button", function () {
        if (confirm("Wollen Sie dieses Buch wirklich löschen?")) {
            deletePlayer(this.value);
        }
    });
});

function loadPlayers() {
    $
        .ajax({
            url: "./resource/player/list",
            dataType: "json",
            type: "GET"
        })
        .done(showPlayers)
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status === 403) {
                window.location.href = "./login.html";
            } else if (xhr.status === 404) {
                $("#message").text("keine Bücher vorhanden");
            }else {
                $("#message").text("Fehler beim Lesen der Bücher");
            }
        })

}

/**
 * shows all players as a table
 *
 * @param playerData all players as an array
 */
function showPlayers(playerData) {

    let table = document.getElementById("football");
    clearTable(table);

    $.each(playerData, function (uuid, player) {
        if (player.name) {
            let row = table.insertRow(-1);
            let cell = row.insertCell(-1);
            cell.innerHTML = player.name;

            cell = row.insertCell(-1);
            cell.innerHTML = player.team.name;

            cell = row.insertCell(-1);
            cell.innerHTML = "<a href='./playeredit.html?uuid=" + uuid + "'>Bearbeiten</a>";

            cell = row.insertCell(-1);
            cell.innerHTML = "<button type='button' id='delete_" + uuid + "' value='" + uuid + "'>Löschen</button>";
        }
    });
}

function clearTable(table) {
    while (table.hasChildNodes()) {
        table.removeChild(table.firstChild);
    }
}

/**
 * send delete request for a player
 * @param playerUUID
 */
function deletePlayer(playerUUID) {
    $
        .ajax({
            url: "./resource/player/delete?uuid=" + playerUUID,
            dataType: "text",
            type: "DELETE",
        })
        .done(function (data) {
            loadPlayers();
            $("#message").text("Spieler gelöscht");

        })
        .fail(function (xhr, status, errorThrown) {
            $("#message").text("Fehler beim Löschen des Spielers");
        })
}
