// Get updated data from the server. Display the updated values of the player's 
// left hand split cards. Hide buttons related to the left hand cards and enable 
// buttons related to the right hand cards.
function splitLeftPlayerStands() {
	
	// On a very slow connection, prevent the player from pressing buttons
	// repeatedly.
	document.getElementById('leftplayerstandsbutton').disabled = true;
	document.getElementById('lefthitplayerbutton').disabled = true;

	// jQuery.getJSON loads JSON-encoded data from the server using a GET HTTP
	// request.
	$.getJSON(
			"splitLeftPlayerStands.do",
			{
			// Optional map or string that is sent to the server with
			// the request.
			},
			// data is the JSON object returned from the server.
			function(data) {

				// Declare variables
				var leftHitPlayerButton, leftPlayerStandsButton, rightHitPlayerButton, rightPlayerStandsButton;

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
				
				// Make the value of the player's hand visible and display it.
				document.getElementById('splitleftplayermessage').style.visibility = 'visible';
				document.getElementById('splitleftplayermessage').innerHTML="The Player's Cards. Total equals " + data.splitHand.splitLeftHandValue;
				
				// Enable buttons that were previously disabled to prevent problems
				// with slow connections.
				document.getElementById('leftplayerstandsbutton').disabled = false;
				document.getElementById('lefthitplayerbutton').disabled = false;
			});
			
}