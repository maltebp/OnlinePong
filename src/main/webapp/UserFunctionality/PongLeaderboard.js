

var leaderboardLoader = document.getElementById("leaderboardLoader");
var leaderboard = document.getElementById("leaderboard");

leaderboard.style.display = "none";
leaderboardLoader.style.display = "none";


function loadLeaderboard(){

    leaderboardLoader.style.display = "inline";

    apiGet( "/getTopTen", function(data) {
        leaderboardLoader.style.display = "none";
        leaderboard.style.display = "inline ";
        var userData = "";
        $.each(data, function (key, value) {
            userData += '<tr>';
            userData += '<td>'+value.username+'</td>';
            userData += '<td>'+value.elo+'</td>';
            userData += '</tr>';
        });
        $('#topTen').append(userData);
    });
}

$(document).ready(function () {
    loadLeaderboard();
});