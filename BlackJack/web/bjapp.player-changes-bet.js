// Pass the drop down menu value selected by the user to the server. 
// Modify the displayed player bet based on the value returned by the server.
// These two values should be the same.
function playerChangesBet(value) {
	// On a very slow connection, prevent the player from pressing the 
	// start button or changing the bet drop down menu repeatedly
	document.getElementById('startgamebutton').disabled = true;
	document.getElementById('betdropdown').disabled = true;
	
	// jQuery.getJSON loads JSON-encoded data from the server using a GET HTTP
	// request.
	$.getJSON(
			"changeBet.do",
			{
				BETSIZE: value
			},
			// data is the JSON object returned from the server
			function(data) {
				document.getElementById('bet').innerHTML = "Your Bet: "
					+ data.playerBet;
			});
	
	// Make the start button and the bet drop down menu usable again.
	document.getElementById('startgamebutton').disabled = false;
	document.getElementById('betdropdown').disabled = false;
}