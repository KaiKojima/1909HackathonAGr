// Get updated data from the server.
// Append the new card to the right hand split 
// card display. If these right hand cards go bust, hide buttons related to the right 
// hand cards and display the "Start A New Game" button and the bet drop down menu.
// If the left hand cards are still in play (not bust) after the right hand cards go bust,
// display the dealers new cards and show who won.
// Display the updated values of the player's right hand split cards and display the
// player's credits.
function splitRightHitPlayer() {
	
	// On a very slow connection, prevent the player from pressing buttons
	// repeatedly.
	document.getElementById('rightplayerstandsbutton').disabled = true;
	document.getElementById('righthitplayerbutton').disabled = true;

	// jQuery.getJSON loads JSON-encoded data from the server using a GET HTTP
	// request.
	$.getJSON(
			"splitRightHitPlayer.do",
			{
			// Optional map or string that is sent to the server with
			// the request.
			},
			// data is the JSON object returned from the server.
			function(data) {
				
				// Declare variables.
				var lastCardInArray, cardImage, rightHitPlayerButton, rightPlayerStandsButton, startGameButton, betDropDown;

				// Append a new player card to the placeholder "splitcardsright".
				lastCardInArray = data.splitHand.splitRightCards.length - 1;
				cardImage = "images/"
						+ data.splitHand.splitRightCards[lastCardInArray].rank
						+ data.splitHand.splitRightCards[lastCardInArray].suit
						+ ".png";
				$('#splitcardsright').append(
						$('<img src= ' + cardImage + '>').fadeIn(2000));

				// Check if the player's right hand cards go bust.			
				if (data.splitHand.splitRightBust=== true) {
					// Show the player's right hand game message. 
					document.getElementById('splitrightgamemessages').style.visibility = 'visible';
					document.getElementById('splitrightgamemessages').innerHTML = data.splitHand.splitRightGameMessage;
	
					// Hide the player's right hand buttons. 
					rightHitPlayerButton = document.getElementById('righthitplayerbutton');
					rightHitPlayerButton.style.display = 'none';

					rightPlayerStandsButton = document.getElementById('rightplayerstandsbutton');
					rightPlayerStandsButton.style.display = 'none';
										
					// Make the starting button and bet drop down menu visible.
					startGameButton = document.getElementById('startgamebutton');
					startGameButton.style.display = 'inline';

					betDropDown = document
					.getElementById('betdropdown');
					betDropDown.style.display = 'inline';
					
					// Make the in-game game message visible and display it.
					document.getElementById('gamemessages').style.visibility = 'visible';
					document.getElementById('gamemessages').innerHTML = data.gameMessage;
					
					// Display the value of the player's credits.
					document.getElementById('credits').innerHTML = "Your Credits: "
						+ data.playerCredits;
					
					// If the right hand has gone bust, but the left hand is still in play,
					// then need to proceed to deal the dealer's cards. Calling the
					// splitRightPlayerStands() will cause this to happen
					if (data.splitHand.splitLeftBust === false){
						//splitRightPlayerStands() is in bjapp.split-right-player-stands.js
						splitRightPlayerStands();
					}
				}
				
				// Make the value of the player's hand visible and display it.
				document.getElementById('splitrightplayermessage').style.visibility = 'visible';
				document.getElementById('splitrightplayermessage').innerHTML="The Player's Cards. Total equals " + data.splitHand.splitRightHandValue;

				// Enable buttons that were previously disabled to prevent problems
				// with slow connections.
				document.getElementById('rightplayerstandsbutton').disabled = false;
				document.getElementById('righthitplayerbutton').disabled = false;
			});

}