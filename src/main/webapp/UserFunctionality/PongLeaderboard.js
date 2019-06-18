
var leaderboardLoader = document.getElementById("leaderboardLoader");
var leaderboard = document.getElementById("leaderboard");

hide(leaderboard);
hide(leaderboardLoader);

function loadLeaderboard(){

    show(leaderboardLoader);

    apiGet( "/getTopTen", function(data) {
        hide(leaderboardLoader);
        show(leaderboard);
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