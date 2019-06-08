var render = function(){
    context.fillStyle = "#000000";
    context.fillRect(0, 0, width, height);
    midCourtGraphics.render();
    player1.render();
    player2.render();
    ball.render();
    bottomLine.render();
};