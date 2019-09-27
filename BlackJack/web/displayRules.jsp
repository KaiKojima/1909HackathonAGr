<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<html>


<head>
<title>All my cards</title>
<link href="<c:url value="/styles.css"/>" rel="Stylesheet"
	type="text/css" />
</head>

<body>
<body>
	<div class="bodywrapper">

		<jsp:include page="/header.jsp" />



		<div class="bigtext">Basic BlackJack Rules</div>
		<p class="paragraph2">
			<br>BlackJack is a variant of Pontoon and "21"</br> <br>Whoever
			is closest to 21 wins</br> <br>If you go over 21, you are "Bust" and
			you lose</br> <br>An ace has a value of 1 or 11</br> <br>The dealer
			has to stand on a hand of 17 to 21</br> <br>The dealer has to hit on
			a hand of 16 or less</br> <br>A normal win pays 1:1</br> <br>A win
			with a blackjack (Ace and 10 or royal) pays 3:2</br> <br>A draw
			(called a push in BlackJack) results in no loss or gain for the
			player</br>
		</p>

		<br></br>
		<div class="bigtext">Advanced BlackJack Rules</div>
		<p class="paragraph2">
			<br>After the opening cards have been dealt, the player can
			double down to double the bet, but only receives one card</br> <br>The
			dealer must stand if the dealer has an ace that, when treated as 11,
			brings the dealer's hand to between 17 and 21, e.g. a 6 and an ace</br> <br>If
			the player has matching cards, the player can split the cards and
			play them as two separate hands</br> <br>10, Jack, Queen and King are
			all considered matching cards for the purposes of a split</br> <br>Winning
			with a BlackJack only pays 1:1 after the player has split</br> <br>Unlike
			in casinos, a single new deck is used for each game</br> <br>Stand is
			known as stick in Pontoon</br> <br>Hit is known as twist in Pontoon</br> <br>Unlike
			in Pontoon, there are no 5 card tricks in Blackjack</br> <br>There
			are many variations to these rules in actual casinos.</br>
		</p>
		<br></br>


		<div class="push"></div>
	</div>

	<div class="footer">
		<jsp:include page="/footer.jsp" />
	</div>


</body>

</html>