/**
 * Object to send through the gameserver to communicate the current state of the game
 *
 * @param code      Server code for understanding communication
 * @param paddle    The players paddle
 * @param ball      The ball
 * @param scores    The current scores
 *
 * @Author  Kristian Andersen
 */
function GameStateObject(code, paddle, ball, scores) {
    this.code = code;
    this.paddle = paddle;
    this.ball = ball;
    this.scores = scores;
}