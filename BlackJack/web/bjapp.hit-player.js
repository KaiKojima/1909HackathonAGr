// Append a new card to the player card display. Get updated data from the server.
// Display the updated values of the player's and dealer's hands. If the player 
//goes bust, change the buttons available to the player.
function hitPlayer() {
	
	// On a very slow connection, prevent the player from pressing buttons
	// repeatedly
	document.getElementById('playerstandsbutton').disabled = true;
	document.getElementById('playerdoublesbutton').disabled = true;
	document.getElementById('hitplayerbutton').disabled = true;
	document.getElementById('playersplitsbutton').disabled = true;

	// jQuery.getJSON loads JSON-encoded data from the server using a GET HTTP
	// request.
	$.getJSON(
			"hitPlayer.do",
			{
			// optional map or string that is sent to the server with
			// the request
			},
			// data is the JSON object returned from the server
			function(data) {

				// declare variables
				var lastCardInArray, cardImage;

				// append a new player card to the placeholder "playercards"
				lastCardInArray = data.playerCards.length - 1;
				cardImage = "images/"
						+ data.playerCards[lastCardInArray].rank
						+ data.playerCards[lastCardInArray].suit
						+ ".png";
				$('#playercards').append(
						$('<img src= ' + cardImage + '>').fadeIn(2000));


				if (data.bustPlayer === true) {
					// If the player goes bust,  make the "start a new game" 
					// button visible and make the "hit player", "player 
					// stands" and "player doubles" buttons invisible.
					// Make the in-game game message visible and display it.
					document.getElementById('gamemessages').style.visibility = 'visible';
					document.getElementById('gamemessages').innerHTML = data.gameMessage;
					
					//Toggle visibility of the bet drop down menu and the "Start a New Game", 
					// "Hit Me", "Stand" and "Double buttons 
					//toggleButtonVisibility() function is in bjapp.toggle-button-visibility
					toggleButtonVisibility();
//					$.getScript("bjapp.shuffle-button-visibility.js");

				} else {
					// If the player doesn't go bust, make the playerStandsButton
					// and the hitPlayerButton clickable again.
					// The playerSplitsButton and playerDoublesButton will stay disabled as 
					// they are only valid when the player has the starting two cards.
					document.getElementById('playerstandsbutton').disabled = false;
					document.getElementById('hitplayerbutton').disabled = false;
				}
				
				//Display the value of the player's and dealer's hands
				document.getElementById('playermessage').innerHTML="The Player's Cards. Total equals " + data.playerHandValue;
				document.getElementById('dealermessage').innerHTML="The Dealer's Cards. Total equals " + data.dealerHandValue;
				
				//Display the value of the player's credits and current bet size
				document.getElementById('credits').innerHTML = "Your Credits: "
					+ data.playerCredits;
				document.getElementById('bet').innerHTML = "Your Bet: "
					+ data.playerBet;
				
			});	
}