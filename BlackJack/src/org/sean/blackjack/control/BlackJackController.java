package org.sean.blackjack.control;

import org.sean.blackjack.domain.Round;
import org.sean.blackjack.services.BlackJackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Sean O'Regan
 * Version 1.2
 * 07.12.2012
 * 
 * This class is a Spring Controller in the Spring Model-View-Controller
 * (MVC) architecture
 * 
 * The following object is injected into this class: an implementation of 
 * the BlackJackService class (in this case the BlackJackServiceImpl class)
 * 
 * In this controller, for each client request, the instance of the
 * Round class is updated by methods of BlackJackService and then
 * returned to the client as a JSON object.
 * 
 * The web.xml file causes ".do" to be appended to all @RequestMapping
 * requests such that @RequestMapping("/blackJackTable") becomes a
 * request to 'domain'/blackJackTable.do
 */

@Controller
//@Scope("session")
public final class BlackJackController {

	@Autowired
	private BlackJackService blackJackService;
	
	private final Round round = Round.getInstance();

	/**
	 * This method will be automatically run when a client sends a request to
	 * the url 'domain'/blackJackTable.do A Spring ModelAndView object is
	 * returned which represents the object passed to ModelAndView and the
	 * associated jsp page, in this case the round object and displayTable.jsp.
	 * 
	 * Initialize the BlackJack table and return the modified instance of the Round object
	 * as a JSON object.
	 * 
	 * @return Round and displayTable.jsp as a ModelAndView
	 */
	@RequestMapping("/blackJackTable")
	public ModelAndView initializeTable() {

		blackJackService.initializeTable(round);
		return new ModelAndView("displayTable", "round", round);
	}

	/**
	 * This method will be automatically run when a jQuery.getJSON function
	 * containing the request to "startGame.do" is called by the client.
	 * 
	 * Start the round of BlackJack by dealing the initial cards and return the modified 
	 * instance of the Round object as a JSON object.
	 * 
	 * @return Round as a JSON object
	 */
	@RequestMapping("/startGame")
	public @ResponseBody
	Round startGame() {

		blackJackService.startRound(round);
		return round;
	}

	/**
	 * This method will be automatically run when a jQuery.getJSON function
	 * containing the request to "playerDoubles.do" is called by the client.
	 * 
	 * Double the value of the player's bet  and return the modified 
	 * instance of the Round object as a JSON object.
	 * 
	 * @return Round as a JSON object
	 */
	@RequestMapping("/playerDoubles")
	public @ResponseBody
	Round playerDoubles() {
		blackJackService.playerDoubles(round);
		return round;
	}

	/**
	 * This method will be automatically run when a jQuery.getJSON function
	 * containing the request to "hitPlayer.do" is called by the client.
	 * 
	 * Add a single card to the player's hand, check if the player is now bust
	 * and return the modified instance of the Round object as a JSON object.
	 * 
	 * @return Round as a JSON object
	 */
	@RequestMapping("/hitPlayer")
	public @ResponseBody
	Round hitPlayer() {

		blackJackService.hitPlayer(round);
		return round;
	}

	/**
	 * This method will be automatically run when a jQuery.getJSON function
	 * containing the request to "playerStands.do" is called by the client.
	 * 
	 * When the player stands, deal cards to the dealer until the dealer stands
	 * or goes bust. Calculate who won and update player credits. Return the modified 
	 * instance of the Round object as a JSON object.
	 * 
	 * @return Round as a JSON object
	 */
	@RequestMapping("/playerStands")
	public @ResponseBody
	Round playerStands() {

		blackJackService.playerStands(round);
		return round;
	}

	/**
	 * This method will be automatically run when a jQuery.getJSON function
	 * containing the request to "playerSplits.do" is called by the client.
	 * 
	 * Split the player's two cards into two new separate hands of one card each, 
	 * one hand displayed on the left of the client screen and the other on the right. 
	 * Return the modified instance of the Round object as a JSON object.
	 * 
	 * @return Round as a JSON object
	 */
	@RequestMapping("/playerSplits")
	public @ResponseBody
	Round playerSplits() {
		blackJackService.playerSplits(round);
		return round;
	}
	
	/**
	 * This method will be automatically run when a jQuery.getJSON function
	 * containing the request to "splitLeftHitPlayer.do" is called by the client.
	 * 
	 * Add a single card to the player's hand on the left of the client screen, 
	 * check if the player is now bust and return the modified instance of the Round 
	 * object as a JSON object.
	 * 
	 * @return Round as a JSON object
	 */
	@RequestMapping("/splitLeftHitPlayer")
	public @ResponseBody
	Round splitLeftHitPlayer() {
		blackJackService.splitLeftHitPlayer(round);
		return round;
	}
	
	/**
	 * This method will be automatically run when a jQuery.getJSON function
	 * containing the request to "splitLeftPlayerStands.do" is called by the client.
	 * 
	 * Indicate that the player has finished adding cards to the hand on the left of the
	 * client screen stands Return the modified instance of the Round object as a JSON object.
	 * 
	 * @return Round as a JSON object
	 */
	@RequestMapping("/splitLeftPlayerStands")
	public @ResponseBody
	Round splitLeftPlayerStands() {
		blackJackService.splitLeftPlayerStands(round);
		return round;
	}
	
	/**
	 * This method will be automatically run when a jQuery.getJSON function
	 * containing the request to "splitRightHitPlayer.do" is called by the client.
	 * 
	 * Add a single card to the player's hand on the right of the client screen, 
	 * check if the player is now bust and return the modified instance of the Round 
	 * object as a JSON object.
	 * 
	 * @return Round as a JSON object
	 */
	@RequestMapping("/splitRightHitPlayer")
	public @ResponseBody
	Round splitRightHitPlayer() {
		blackJackService.splitRightHitPlayer(round);
		return round;
	}
	
	/**
	 * This method will be automatically run when a jQuery.getJSON function
	 * containing the request to "splitRightPlayerStands.do" is called by the client.
	 * 
	 * Now that the player is finished playing both hands, deal cards to the dealer until 
	 * the dealer either goes bust or stands. Check if the player's active hands win against 
	 * the dealer's hand. Return the modified instance of the Round object as a JSON object.
	 * 
	 * @return Round as a JSON object
	 */
	@RequestMapping("/splitRightPlayerStands")
	public @ResponseBody
	Round splitRightPlayerStands() {
		blackJackService.splitRightPlayerStands(round);
		return round;
	}
	
	/**
	 * This method will be automatically run when a jQuery.getJSON function
	 * containing the request to "changeBet.do" is called by the client.
	 * 
	 * Change the size of the player's bet and return the modified instance of the Round 
	 * object as a JSON object.
	 * 
	 * @return Round as a JSON object
	 */
	@RequestMapping("/changeBet")
	public @ResponseBody
	Round changeBet(@RequestParam("BETSIZE") String betSize) {
		blackJackService.changeBet(round, betSize);
		return round;
	}

	/**
	 * This method will be automatically run when a client sends a request to
	 * the url 'domain'/showRules.do A Spring ModelAndView object is returned
	 * which represents the object passed to it and the associated jsp page in
	 * this case only the displayRules.jsp is returned. No model object is
	 * returned.
	 * 
	 * Return the displayRules.jsp page for display at the client
	 * 
	 * @return displayRules.jsp as a ModelAndView
	 */
	@RequestMapping("/showRules")
	public ModelAndView showRules() {
		return new ModelAndView("displayRules");
	}

}
