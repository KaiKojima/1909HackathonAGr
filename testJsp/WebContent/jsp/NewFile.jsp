<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Blackjack</title>
</head>
<body>

<div>
    <h1>Dealers Hand</h1>
    ${game.dealersHand.toString()}

    <h2>Totals</h2>
    ${game.dealersHand.finalTotal()}

    ${game.dealersHand.result()}

</div>

<div>
    <h1>Players Hand</h1>
    ${game.playersHand.toString()}

    <h2>Totals</h2>
    ${game.playersHand.finalTotal()}

    ${game.playersHand.result()}

</div>

<div>
    <h1>Result</h1>
    ${game.result()}
</div>

<div>
    <form method="POST">
        <input value="Deal" name="deal" type="submit" id="dealButton" class="submit_button">
    </form>
</div>

</body>
</html>