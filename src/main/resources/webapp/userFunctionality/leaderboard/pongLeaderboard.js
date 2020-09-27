
var leaderboardLoader = document.getElementById("leaderboardLoader");
var leaderboard = document.getElementById("leaderboard");

hide(leaderboard);
hide(leaderboardLoader);

function loadLeaderboard(){
    show(leaderboardLoader);

    apiGet( "/getTopTen", function(data) {
        console.log(data);
        hide(leaderboardLoader);
        show(leaderboard);
        var leaderboardData = "";
        $.each(data.users, function (key, value) {
            if(value.username !== '')
            leaderboardData += '<tr>';
            leaderboardData += '<td>'+value.username+'</td>';
            leaderboardData += '<td>'+value.elo+'</td>';
            leaderboardData += '</tr>';
        });
        $('#topTen').append(leaderboardData);
    });
}

$(document).ready(function () {
    loadLeaderboard();
});