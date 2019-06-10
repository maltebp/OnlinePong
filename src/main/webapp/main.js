$(function () {

    var $order = $('#order');

    $.ajax({
        type: 'GET',
        url: 'http://192.168.43.147:8080/api/json',
        success: function(data){
            console.log(data);
        }

    });


    var ball2 = "{\"x\":10,\"y\":10,\"xVel\":5,\"yVel\":3}";
    var ball3 = {
        x: 10,
        y: 10,
        xVel: 5,
        yVel: 3
    };

    console.log("success1");

    $.ajax({
        type: 'POST',
        //Manglede et parameter. Den kunne ikke genkende at det var en POST message
        url: 'http://192.168.43.147:8080/api/json',

        contentType: "application/json",
        data: JSON.stringify(ball3),
        success: function(data){
            console.log("success2")
            console.log(data);
        },
        error: function(){
            console.log("fail");
        }


    });

    console.log("success3");
    });