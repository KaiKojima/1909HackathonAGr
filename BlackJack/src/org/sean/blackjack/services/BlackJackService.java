package org.sean.blackjack.services;

import org.sean.blackjack.domain.Round;

/**
 * This Interface is part of Spring MVC's Service Layer. 
 * It is implemented by BlackJackServiceImpl. The instance of the 
 * Round class is passed to it's methods and updated appropriately 
 * for that stage of the BlackJack game.
 *
 */
public interface BlackJackService {

	void initializeTable(Round round);

	void startRound(Round round);

	void hitPlayer(Round round);

	void playerDoubles(Round round);
	
	void playerStands(Round round);

	void changeBet(Round round, String betSize);

	void playerSplits(Round round);

	void splitLeftHitPlayer(Round round);

	void splitLeftPlayerStands(Round round);

	void splitRightHitPlayer(Round round);

	void splitRightPlayerStands(Round round);

}
