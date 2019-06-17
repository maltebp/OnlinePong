
$(document).ready(function () {
    $.getJSON(url + "/getTopTen", function (data) {
        var userData = "";
        $.each(data, function (key, value) {
            userData += '<tr>';
            userData += '<td>'+value.username+'</td>';
            userData += '<td>'+value.elo+'</td>';
            userData += '</tr>';
        });
        $('#topTen').append(userData);
    });
});