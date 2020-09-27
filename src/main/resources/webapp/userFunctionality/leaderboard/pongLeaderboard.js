
var leaderboardLoader = document.getElementById("leaderboardLoader");
var leaderboard = document.getElementById("leaderboard");

hide(leaderboard);
hide(leaderboardLoader);

function loadLeaderboard(){
    show(leaderboardLoader);


    apiGet( "/topten", function(data) {
        hide(leaderboardLoader);
        show(leaderboard);
        var leaderboardData = "";
        $.each(data.result.users, function (key, value) {
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