// Get updated data from the server.
// Display the dealers new cards and the value of the dealer's hand.
// Show who won between each of the player's active hands and the dealer.
// Display the updated values of the player's right hand split cards and display the
// player's credits.
// Hide the buttons corresponding with the right hand cards and display the "Start A New
// Game" button and the bet drop down menu.
function splitRightPlayerStands() {
	
	// On a very slow connection, prevent the player from pressing buttons
	// repeatedly.
	document.getElementById('rightplayerstandsbutton').disabled = true;
	document.getElementById('righthitplayerbutton').disabled = true;

	// jQuery.getJSON loads JSON-encoded data from the server using a GET HTTP
	// request.
	$.getJSON(
			"splitRightPlayerStands.do",
			{
			// Optional map or string that is sent to the server with
			// the request.
			},
			// data is the JSON object returned from the server.
			function(data) {

				// Declare variables.
				var index, cardImage, rightHitPlayerButton, rightPlayerStandsButton, startGameButton, betDropDown;
				// Clear the dealer's card and image of the hidden card
				// from the screen.
				document.getElementById('dealercards').innerHTML = '';

				// Instantly display the first dealer card. This is the
				// card that was visible from the start of the round.
				// Fade in the other dealer cards.
				for (index = 0; index < data.dealerCards.length; index += 1) {
					if (index === 0) {
						cardImage = "images/"
								+ data.dealerCards[index].rank
								+ data.dealerCards[index].suit
								+ ".png";
						$('#dealercards').append(
								$('<img src= ' + cardImage + '>'));
					} else {
						cardImage = "images/"
								+ data.dealerCards[index].rank
								+ data.dealerCards[index].suit
								+ ".png";
						$('#dealercards').append(
								$('<img src= ' + cardImage + '>')
										.fadeIn(3000));
					}
				}
				
				// Display the value of the dealer's hand.
				document.getElementById('dealermessage').innerHTML="The Dealer's Cards. Total equals " + data.dealerHandValue;

				// Make the in-game game message visible and display it.
				document.getElementById('gamemessages').style.visibility = 'visible';
				document.getElementById('gamemessages').innerHTML = data.gameMessage;
				
				// Make the split in-game game messages visible and display them.
				document.getElementById('splitleftgamemessages').style.visibility = 'visible';
				document.getElementById('splitleftgamemessages').innerHTML = data.splitHand.splitLeftGameMessage;				
				document.getElementById('splitrightgamemessages').style.visibility = 'visible';
				document.getElementById('splitrightgamemessages').innerHTML = data.splitHand.splitRightGameMessage;
				
				// Make the value of the player's right hand cards visible and display it.
				document.getElementById('splitrightplayermessage').style.visibility = 'visible';
				document.getElementById('splitrightplayermessage').innerHTML="The Player's Cards. Total equals " + data.splitHand.splitRightHandValue;
				
				// Display the value of the player's credits.
				document.getElementById('credits').innerHTML = "Your Credits: "
						+ data.playerCredits;
				
				// Make the starting button and bet drop down menu visible.
				startGameButton = document.getElementById('startgamebutton');
				startGameButton.style.display = 'inline';

				betDropDown = document
				.getElementById('betdropdown');
				betDropDown.style.display = 'inline';

				// Hide the right hand player cards buttons.
				rightHitPlayerButton = document.getElementById('righthitplayerbutton');
				rightHitPlayerButton.style.display = 'none';

				rightPlayerStandsButton = document.getElementById('rightplayerstandsbutton');
				rightPlayerStandsButton.style.display = 'none';

				// Enable buttons that were previously disabled to prevent problems
				// with slow connections.
				document.getElementById('rightplayerstandsbutton').disabled = false;
				document.getElementById('righthitplayerbutton').disabled = false;

			});
			
}