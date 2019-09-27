// Get updated data from the server.
// Append the new card to the left hand split 
// card display. Display the updated values of the player's left hand split 
// cards. If these left hand cards go bust, hide buttons related to the left 
// hand cards and enable buttons related to the right hand cards.
function splitLeftHitPlayer() {
	
	// On a very slow connection, prevent the player from pressing buttons
	// repeatedly.
	document.getElementById('leftplayerstandsbutton').disabled = true;
	document.getElementById('lefthitplayerbutton').disabled = true;

	// jQuery.getJSON loads JSON-encoded data from the server using a GET HTTP
	// request.
	$.getJSON(
			"splitLeftHitPlayer.do",
			{
			// optional map or string that is sent to the server with
			// the request
			},
			// data is the JSON object returned from the server
			function(data) {
				
				// Declare variables
				var lastCardInArray, cardImage, leftHitPlayerButton, leftPlayerStandsButton, rightHitPlayerButton, rightPlayerStandsButton;

				// append the new player card to the placeholder "splitcardsleft"
				lastCardInArray = data.splitHand.splitLeftCards.length - 1;
				cardImage = "images/"
						+ data.splitHand.splitLeftCards[lastCardInArray].rank
						+ data.splitHand.splitLeftCards[lastCardInArray].suit
						+ ".png";
				$('#splitcardsleft').append(
						$('<img src= ' + cardImage + '>').fadeIn(2000));

				// Check if the player's left hand cards go bust.
				if (data.splitHand.splitLeftBust === true) {
					// Show the player's left hand game message.
					document.getElementById('splitleftgamemessages').style.visibility = 'visible';
					document.getElementById('splitleftgamemessages').innerHTML = data.splitHand.splitLeftGameMessage;
					
					// Hide the player's left hand buttons and make the player's right hand
					// buttons visible.
					leftHitPlayerButton = document.getElementById('lefthitplayerbutton');
					leftHitPlayerButton.style.display = 'none';

					leftPlayerStandsButton = document.getElementById('leftplayerstandsbutton');
					leftPlayerStandsButton.style.display = 'none';					
					
					rightHitPlayerButton = document
					.getElementById('righthitplayerbutton');
					rightHitPlayerButton.style.display = 'inline';

					rightPlayerStandsButton = document
					.getElementById('rightplayerstandsbutton');
					rightPlayerStandsButton.style.display = 'inline';
					
					// Display the value of the player's credits.
					document.getElementById('credits').innerHTML = "Your Credits: "
						+ data.playerCredits;
				}
				
				// Make the value of the player's left hand cards visible and display it.
				document.getElementById('splitleftplayermessage').style.visibility = 'visible';
				document.getElementById('splitleftplayermessage').innerHTML="The Player's Cards. Total equals " + data.splitHand.splitLeftHandValue;
			
				// Enable buttons that were previously disabled to prevent problems
				// with slow connections.
				document.getElementById('leftplayerstandsbutton').disabled = false;
				document.getElementById('lefthitplayerbutton').disabled = false;

			});	
}