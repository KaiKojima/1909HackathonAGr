// Clear all in-game messages from any previous game.
// Clear all cards displayed from any previous game.
// Get updated data from the server.
// Display the dealer's initial card. Append the image of a hidden card
// to the dealer's initial card.
// Display the player's initial two cards.
// Display the values of the player's and dealer's hands.
// Change the buttons available to the player.
function startGame() {
	// declare variables
	var splitCardsLeft, splitCardsRight, playerCards, playerMessage;
	
	// On a very slow connection, prevent the player from pressing the 
	// start button or changing the bet drop down menu repeatedly
	document.getElementById('startgamebutton').disabled = true;
	document.getElementById('betdropdown').disabled = true;
	
	// At the start of each game, clear the dealer cards, player cards and
	// in-game messages
	document.getElementById('dealercards').innerHTML = '';
	document.getElementById('playercards').innerHTML = '';
	document.getElementById('gamemessages').innerHTML = '';
	document.getElementById('splitleftplayermessage').innerHTML = '';
	document.getElementById('splitrightplayermessage').innerHTML = '';
	document.getElementById('splitleftgamemessages').innerHTML = '';
	document.getElementById('splitrightgamemessages').innerHTML = '';
	document.getElementById('splitcardsleft').innerHTML = '';
	document.getElementById('splitcardsright').innerHTML = '';
	
	//Hide the split cards, if any.
	splitCardsLeft = document.getElementById('splitcardsleft');
	splitCardsLeft.style.display = 'none';
	
	splitCardsRight = document.getElementById('splitcardsright');
	splitCardsRight.style.display = 'none';

	// Make the regular player cards visible.
	// It is necessary to set the display to block so that the cards are centered.
	playerCards = document.getElementById('playercards');
	playerCards.style.display = 'block';
	
	// Make the regular player message visible.
	// It is necessary to set the display to block so that the message is centered.
	playerMessage = document.getElementById('playermessage');
	playerMessage.style.display = 'block';
	
	// jQuery.getJSON loads JSON-encoded data from the server using a GET HTTP
	// request.
	$.getJSON(
			"startGame.do",
			{
			// optional map or string that is sent to the server with
			// the request
			},
			// data is the JSON object returned from the server
			function(data) {

				// declare variables
				var index, backOfCard, cardImage, startGameButton, hitPlayerButton, playerStandsButton, playerSplitsButton, dealerMessage, playerDoublesButton, betDropDown;
				
				// Display the player credits and player bet to the
				// placeholders "credits" and "bet"
				document.getElementById('credits').innerHTML = "Your Credits: "
						+ data.playerCredits;
				document.getElementById('bet').innerHTML = "Your Bet: "
						+ data.playerBet;

				// display the initial dealer card to the placeholder
				// "dealercards"
				cardImage = "images/" + data.dealerCards[0].rank
						+ data.dealerCards[0].suit + ".png";
				$('#dealercards').append(
						$('<img src= ' + cardImage + '>').fadeIn(2000));

				// append the image of a hidden card to the placeholder
				// "dealercards". This is a dummy for display purposes
				// only. In effect
				// the dealer is only dealt one real card at the start
				// of the game.
				backOfCard = "images/BACKOFCARD.png";
				$('#dealercards')
						.append(
								$('<img src= ' + backOfCard + '>')
										.fadeIn(2000));

				// display the initial player cards to the placeholder
				// "playercards"
				for (index = 0; index < data.playerCards.length; index += 1) {
					cardImage = "images/"
							+ data.playerCards[index].rank
							+ data.playerCards[index].suit + ".png";
					$('#playercards').append(
							$('<img src= ' + cardImage + '>').fadeIn(
									2000));
				}
				

				// Display the value of the player's and dealer's hands
				// to the placeholders "playermessage" and
				// "dealermessage". Make these messages visible.
				document.getElementById('playermessage').innerHTML = "The Player's Cards. Total equals "
						+ data.playerHandValue;
				document.getElementById('dealermessage').innerHTML = "The Dealer's Cards. Total equals "
						+ data.dealerHandValue;
				dealerMessage = document
						.getElementById('dealermessage');
				dealerMessage.style.visibility = 'visible';
				playerMessage = document
						.getElementById('playermessage');
				playerMessage.style.visibility = 'visible';

				// make the bet drop down menu and "start a new game" button invisible and make
				// the "hit player", "player stands" and "player doubles"  buttons visible and 
				// clickable.
				startGameButton = document
						.getElementById('startgamebutton');
				startGameButton.style.display = 'none';
				
				betDropDown = document
						.getElementById('betdropdown');
				betDropDown.style.visibility="hidden";

				hitPlayerButton = document
						.getElementById('hitplayerbutton');
				hitPlayerButton.style.display = 'inline';
				hitPlayerButton.disabled = false;

				playerStandsButton = document
						.getElementById('playerstandsbutton');
				playerStandsButton.style.display = 'inline';
				playerStandsButton.disabled = false;

				playerDoublesButton = document
						.getElementById('playerdoublesbutton');
				playerDoublesButton.style.display = 'inline';
				playerDoublesButton.disabled = false;
				
				// If splitting the cards is allowed, make the "Split Cards" button visible
				if (data.playerCanSplit) {
					playerSplitsButton = document
						.getElementById('playersplitsbutton');
					playerSplitsButton.style.display = 'inline';
					playerSplitsButton.disabled = false;
				}
				
			});
	
	// Make the start button and the bet drop down menu usable again.
	document.getElementById('startgamebutton').disabled = false;
	document.getElementById('betdropdown').disabled = false;
			
}