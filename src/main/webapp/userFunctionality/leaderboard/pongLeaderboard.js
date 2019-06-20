
var leaderboardLoader = document.getElementById("leaderboardLoader");
var leaderboard = document.getElementById("leaderboard");

hide(leaderboard);
hide(leaderboardLoader);

function loadLeaderboard(){

    show(leaderboardLoader);

    apiGet( "/getTopTen", function(json) {
        hide(leaderboardLoader);
        show(leaderboard);
        var userData = "";
        $.each(json, function (key, value) {
            if(value.username !== '')
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