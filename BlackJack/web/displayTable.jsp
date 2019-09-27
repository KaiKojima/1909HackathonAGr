<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>

<head>
<title>Round Of Blackjack</title>
<link href="<c:url value="/styles.css"/>" rel="Stylesheet"
	type="text/css" />

</head>

<body>
	<div class="bodywrapper">



		<jsp:include page="/header.jsp" />

		<div class="wrap2">
			<!-- Holder for the player's cards split to the left -->
			<div id="credits" class="msg5" title="This is your credit total">Holder
				for player credits</div>
			<!-- Holder for the player's cards split to the right -->
			<div id="bet" class="msg6"
				title="This is the total you have bet for this game">Holder
				for player bet</div>
		</div>


		<!-- Holder for the dealer's cards -->
		<div id="dealercards" class="card"></div>

		<div id="dealermessage" class="msg2">Holder for the dealer
			message</div>

		<!-- Holder for the player's cards -->
		<div id="playercards" class="card"></div>

		<div id="playermessage" class="msg2">Holder for the player
			message</div>

		<div id="gamemessages" class="msg2">Holder for the in-game
			messages</div>



	<!-- This block of javascript also includes some jstl.
	That is why it needs to be placed in the .jsp file itself and not called from a separate
	javascript file. I need to investigate if there is a better method to do this. Mixing jstl and JS probably bad -->
	<script>
		document.getElementById('credits').innerHTML = "Your Credits: " + ${round.playerCredits};
		document.getElementById('bet').innerHTML = "Your Bet: " + ${round.playerBet};
	</script>


		<p class="paragraph1">
			<button id="startgamebutton" type="button" class="btn1"
				onclick="startGame();">Start A New Game</button>

			<button id="hitplayerbutton" type="button" class="btn2"
				onclick="hitPlayer();" title="Get Another Card">Hit Me</button>

			<button id="playerstandsbutton" type="button" class="btn2"
				onclick="playerStands();" title="Stick With These Cards">Stand</button>

			<button id="playerdoublesbutton" type="button" class="btn2"
				onclick="playerDoubles();"
				title="Double Your Bet and Get One More Card. This Option Only Exists When You Have Two Cards">Double</button>

			<button id="playersplitsbutton" type="button" class="btn2"
				onclick="playerSplits();"
				title="Split your cards into two hands. Your bet applies to each hand so, in effect, you double your bet. BlackJacks are treated as normal hands of 21 when you split.">Split
				Cards</button>
		</p>

		<p class="paragraph1">
			<!-- Drop down list to select bet size -->
			<select id="betdropdown" name="setBetSize"
				onchange="playerChangesBet(this.value);"
				title="Change The Size Of Your Bet">
				<option value="10" selected="selected">Bet 10 Credits</option>
				<option value="20">Bet 20 Credits</option>
				<option value="40">Bet 40 Credits</option>
				<option value="80">Bet 80 Credits</option>
				<option value="160">Bet 160 Credits</option>
			</select>
		</p>

		<div class="wrap1">
			<!-- Holder for the player's cards split to the left -->
			<div id="splitcardsleft" class="left"></div>
			<!-- Holder for the player's cards split to the right -->
			<div id="splitcardsright" class="right"></div>
		</div>

		<div class="wrap2">
			<div id="splitleftplayermessage" class="msg3">Holder for the
				left player message</div>

			<div id="splitrightplayermessage" class="msg4">Holder for the
				right player message</div>
		</div>

		<div class="wrap2">
			<div id="splitleftgamemessages" class="msg3">Holder for the
				left in-game messages</div>

			<div id="splitrightgamemessages" class="msg4">Holder for the
				right in-game messages</div>
		</div>

		<div class="wrap2">
			<button id="lefthitplayerbutton" type="button" class="btn3"
				onclick="splitLeftHitPlayer();" title="Get Another Card">Hit
				Me</button>

			<button id="leftplayerstandsbutton" type="button" class="btn3"
				onclick="splitLeftPlayerStands();" title="Stick With These Cards">Stand</button>

			<button id="rightplayerstandsbutton" type="button" class="btn4"
				onclick="splitRightPlayerStands();" title="Stick With These Cards">Stand</button>

			<button id="righthitplayerbutton" type="button" class="btn4"
				onclick="splitRightHitPlayer();" title="Get Another Card">Hit
				Me</button>
		</div>


		<script
			src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>

		<script src="bjapp.start.js"></script>
		<script src="bjapp.hit-player.js"></script>
		<script src="bjapp.player-stands.js"></script>
		<script src="bjapp.player-doubles.js"></script>
		<script src="bjapp.player-splits.js"></script>
		<script src="bjapp.player-changes-bet.js"></script>
		<script src="bjapp.split-left-hit-player.js"></script>
		<script src="bjapp.split-right-hit-player.js"></script>
		<script src="bjapp.split-left-player-stands.js"></script>
		<script src="bjapp.split-right-player-stands.js"></script>
		<script src="bjapp.toggle-button-visibility.js"></script>

		<div class="push"></div>
	</div>
	
	<div class="footer">
		<jsp:include page="/footer.jsp" />
	</div>



</body>

</html>
