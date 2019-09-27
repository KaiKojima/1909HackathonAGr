// This function causes the player to split the two opening cards
// into 2 parallel hands of one card each.
//
// Hide the "hit player", "stand", double" and "split" buttons.
// Get updated data from the server.
// Display the dealer's initial card. Append the image of a hidden card
// to the dealer's initial card.
// Display the player's initial two cards.
// Display the values of the player's and dealer's hands.
// Change the buttons available to the player.

function playerSplits() {
	// Declare variables.
	var hitPlayerButton, playerStandsButton, playerDoublesButton, playerSplitsButton;
	
	// Hide the "hit player", "stand", double" and "split" buttons.
	hitPlayerButton = document.getElementById('hitplayerbutton');
	hitPlayerButton.style.display = 'none';

	playerStandsButton = document.getElementById('playerstandsbutton');
	playerStandsButton.style.display = 'none';

	playerDoublesButton = document.getElementById('playerdoublesbutton');
	playerDoublesButton.style.display = 'none';

	playerSplitsButton = document.getElementById('playersplitsbutton');
	playerSplitsButton.style.display = 'none';

	// jQuery.getJSON loads JSON-encoded data from the server using a GET HTTP
	// request.
	$.getJSON(
			"playerSplits.do",
			{
			// Optional map or string that is sent to the server with
			// the request.
			},
			// data is the JSON object returned from the server.
			function(data) {
				// Declare variables.
				var playerCards, playerMessage, cardImage, leftHitPlayerButton, leftPlayerStandsButton, splitCardsLeft, splitCardsRight;

				// Hide the existing player cards and player message.
				playerCards = document.getElementById('playercards');
				playerCards.style.display = 'none';

				playerMessage = document.getElementById('playermessage');
				playerMessage.style.display = 'none';
				
				// Show the updated game message
				document.getElementById('gamemessages').style.visibility = 'visible';
				document.getElementById('gamemessages').innerHTML = data.gameMessage;
				
				// Make viewable the placeholders for the left and right split cards.
				splitCardsLeft = document.getElementById('splitcardsleft');
				splitCardsLeft.style.display = 'inline';
				
				splitCardsRight = document.getElementById('splitcardsright');
				splitCardsRight.style.display = 'inline';

				// Append the left split card to the placeholder "splitcardsleft".
				cardImage = "images/"
						+ data.splitHand.splitLeftCards[0].rank
						+ data.splitHand.splitLeftCards[0].suit + ".png";
				$('#splitcardsleft').append(
						$('<img src= ' + cardImage + '>').fadeIn(
								2000));
				
				// Append the right split card to the placeholder "splitcardsright".							
				cardImage = "images/"
					+ data.splitHand.splitRightCards[0].rank
					+ data.splitHand.splitRightCards[0].suit + ".png";
				$('#splitcardsright').append(
						$('<img src= ' + cardImage + '>').fadeIn(
								2000));

				// Make viewable and clickable the button "left hit player".
				leftHitPlayerButton = document
					.getElementById('lefthitplayerbutton');
				leftHitPlayerButton.style.display = 'inline';
				leftHitPlayerButton.disabled = false;

				// Make viewable and clickable the button "right hit player".
				leftPlayerStandsButton = document
					.getElementById('leftplayerstandsbutton');
				leftPlayerStandsButton.style.display = 'inline';
				leftPlayerStandsButton.disabled = false;
				
				// Display the value of the player's credits and current bet size.
				document.getElementById('credits').innerHTML = "Your Credits: "
					+ data.playerCredits;
				document.getElementById('bet').innerHTML = "Your Bet: "
					+ data.playerBet;

			});
			
}