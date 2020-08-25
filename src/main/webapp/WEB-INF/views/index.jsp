<html>
<head>
    <title>Guess Game</title>
</head>
<body>
    Welcome to Guess Game!
    <br>
    Guess a number between ${guessGame.min} and ${guessGame.max}.
    <form method="POST">
        <input type=text name=param>
        <input type=submit value="GUESS">
    </form>
    <span style="color:red">${guessGame.error}</span>
    <br>
    <br>
</body>
</html>