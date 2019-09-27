package org.sean.blackjack.services;

import org.sean.blackjack.domain.Consts;
import org.sean.blackjack.domain.Deck;
import org.sean.blackjack.domain.Round;
import org.springframework.stereotype.Service;

/**
 * This Class is part of Spring MVC's Service Layer. It implements
 * BlackJackService. The instance of the Round class is passed to it's methods
 * and updated appropriately for that stage of the BlackJack game.
 * 
 */
@Service
public final class BlackJackServiceImpl implements BlackJackService {
	
	private Deck deck;
	private boolean playerDoubledBet;

	/**
	 * Initialize the Round object so that it has the values that need to be
	 * seen at the client when the player "sits down at" the BlackJack table.
	 * 
	 * @param round
	 *            - The round object represents the current state of the
	 *            BlackJack game
	 */
	@Override
	public void initializeTable(Round round) {
		if (round == null) {
			throw new IllegalArgumentException("Invalid Value " + round);//fail fast			
		}
		
		round.setPlayerCredits(Consts.STARTING_CREDITS);
		round.setPlayerBet(Consts.STARTING_BET);
	}	

	/**
	 * Change the player's bet
	 * 
	 * @param round
	 *            - The round object represents the current state of the
	 *            BlackJack game
	 * @param betSize
	 *            - a String received from the client and set by a drop down
	 *            list
	 */
	@Override
	public void changeBet(Round round, String betSize) {
		if (round == null) {
			throw new IllegalArgumentException("Invalid Value " + round);//fail fast			
		}		
		if (Integer.valueOf(betSize) <= 0) {
			throw new IllegalArgumentException("Invalid Value " + betSize);//fail fast			
		}
		
		round.setPlayerBet(Integer.valueOf(betSize));
		// In case the player just doubled the bet, set playerDoubledBet
		// to false so that the new bet value set by the client will not be
		// modified by this.startRound().
		playerDoubledBet = false;
	}

	/**
	 * Clear the values of all fields in the round object except for the
	 * player's credits and the player's bet size. Remove the player's bet from
	 * the player's credits. Deal two visible cards to the player. Deal one
	 * visible and one hidden card to the dealer.
	 * 
	 * @param round
	 *            - The round object represents the current state of the
	 *            BlackJack game
	 */
	@Override
	public void startRound(Round round) {
		if (round == null) {
			throw new IllegalArgumentException("Invalid Value " + round);//fail fast			
		}
		
		boolean playerFinishedDrawingCards = false;
		round.setSplitHand(null);
		round.setBustPlayer(false);
		round.setPlayerHasBlackJack(false);
		round.setPlayerCanSplit(false);
		round.setGameMessage(Consts.BLANK_MESSAGE);
		// If the player doubled the bet the previous round, set it
		// back to the correct value.
		if (this.playerDoubledBet) {
			round.setPlayerBet(round.getPlayerBet() / 2);
			this.playerDoubledBet = false;
		}
		round.getDealerCards().clear();
		round.getPlayerCards().clear();

		// Get a new 52 card deck
		deck = new Deck();

		// Deal a single card to the dealer.
		// The dealer's hidden card is added to the display in start.js
		// Note that there is no actual hidden card. The image of a hidden card
		// is displayed
		// at the client and represents the dealer's second card. The dealer's
		// actual second card is added
		// after the player stands.
		round.getDealerCards().add(deck.dealRandomCard());

		// Deal the two starting cards to the player
		round.getPlayerCards().add(deck.dealRandomCard());
		round.getPlayerCards().add(deck.dealRandomCard());

		round.calculateHandValues(playerFinishedDrawingCards);
		round.checkIfPlayerCanSplit();
	}

	/**
	 * Deal a single card to the player and check to see if the player has gone
	 * bust.
	 * 
	 * @param round
	 *            - The round object represents the current state of the
	 *            BlackJack game
	 */
	@Override
	public void hitPlayer(Round round) {
		if (round == null) {
			throw new IllegalArgumentException("Invalid Value " + round);//fail fast			
		}
		
		boolean isPlayer = true;
		boolean playerFinishedDrawingCards = false;
		boolean isSplit = false;
		round.getPlayerCards().add(deck.dealRandomCard());
		round.checkBust(round.getPlayerCards(), isPlayer, isSplit);
		round.calculateHandValues(playerFinishedDrawingCards);
	}

	/**
	 * Double the player's bet. The player receives only a single card when
	 * doubling. Check if the player has gone bust. Deal cards to the dealer
	 * until the dealer either goes bust or stands. When the dealer stands,
	 * check who won.
	 * 
	 * @param round
	 *            - The round object represents the current state of the
	 *            BlackJack game
	 */
	@Override
	public void playerDoubles(Round round) {
		if (round == null) {
			throw new IllegalArgumentException("Invalid Value " + round);//fail fast			
		}
		
		boolean isPlayer = true;
		boolean playerFinishedDrawingCards = false;
		boolean isSplit = false;
		// Double the player's bet
		round.setPlayerBet(2 * round.getPlayerBet());
		this.playerDoubledBet = true;
		// Card card;
		// card = deck.dealRandomCard();
		// round.getPlayerCards().add(card);
		round.getPlayerCards().add(deck.dealRandomCard());
		playerFinishedDrawingCards = true;
		// if (round.checkBustPlayer()) {
		if (round.checkBust(round.getPlayerCards(), isPlayer, isSplit)) {
			round.calculateHandValues(playerFinishedDrawingCards);
			return;
		}
		this.playerStands(round);
	}

	/**
	 * When the player stands, first of all, check if the player has a winning
	 * blackjack. If not, deal cards to the dealer until the dealer either goes
	 * bust or stands. When the dealer stands, check who won.
	 * 
	 * @param round
	 *            - The round object represents the current state of the
	 *            BlackJack game
	 */
	@Override
	public void playerStands(Round round) {
		if (round == null) {
			throw new IllegalArgumentException("Invalid Value " + round);//fail fast			
		}
		
		boolean isPlayer = false;
		boolean playerFinishedDrawingCards = true;
		boolean isSplit = false;
		// Check if the player has blackjack and that the dealer's visible card
		// excludes the possibility of making a jackpot (visible card has value
		// of 2 to 9).
		// If so, immediately run the round.checkWhoWon() which will assign the
		// win to the player
		if (round.hasPlayerABlackJack() && round.dealerCanNotMakeBlackJack()) {
			round.checkWhoWon();
			round.calculateHandValues(playerFinishedDrawingCards);
			return;
		}

		// Deal cards to the dealer until the dealer either goes bust or stands
		while (!round.dealerMustStand()) {
			// Card card = deck.dealRandomCard();
			// round.getDealerCards().add(card);
			round.getDealerCards().add(deck.dealRandomCard());
			/*
			 * If the player has a BlackJack, then the dealer should not be
			 * dealt more than two cards in total
			 */
			if (round.hasPlayerABlackJack()
					&& round.getDealerCards().size() == 2) {
				round.checkWhoWon();
				round.calculateHandValues(playerFinishedDrawingCards);
				return;
			}
			// if (round.checkBustDealer()) {
			if (round.checkBust(round.getDealerCards(), isPlayer, isSplit)) {
				round.calculateHandValues(playerFinishedDrawingCards);
				return;
			}
		}
		// The dealer stands. Check who won.
		round.checkWhoWon();
		round.calculateHandValues(playerFinishedDrawingCards);
	}

	/**
	 * Splits the player's beginning two cards into two different hands of one
	 * card each. This will enable the player to play out each hand separately.
	 * 
	 * @param round
	 *            - The round object represents the current state of the
	 *            BlackJack game
	 */
	@Override
	public void playerSplits(Round round) {
		if (round == null) {
			throw new IllegalArgumentException("Invalid Value " + round);//fail fast			
		}
		
		round.playerSplits();
	}

	/**
	 * Deal a single card to the player's hand on the left of the client screen.
	 * Check to see if the player has gone bust.
	 * 
	 * @param round
	 *            - The round object represents the current state of the
	 *            BlackJack game
	 */
	@Override
	public void splitLeftHitPlayer(Round round) {
		if (round == null) {
			throw new IllegalArgumentException("Invalid Value " + round);//fail fast			
		}
		
		boolean isSplitLeft = true;
		boolean splitLeftFinishedDrawingCards = false;
		boolean bust = false;
		round.getSplitHand().getSplitLeftCards().add(deck.dealRandomCard());
		bust = round.getSplitHand().checkIfSplitHandBust(
				round.getSplitHand().getSplitLeftCards(), isSplitLeft);
		if (bust) {
			round.setPlayerCredits(round.getPlayerCredits()
					- round.getPlayerBet());
		}
		round.getSplitHand().calculateSplitHandValues(
				round.getSplitHand().getSplitLeftCards(),
				splitLeftFinishedDrawingCards , isSplitLeft);
	}

	/**
	 * The player has finished adding cards to the hand on the left of the client 
	 * screen. Calculate the hand value for this hand converting aces to 11 if that
	 * does not cause the hand to go bust.
	 * 
	 * @param round
	 *            - The round object represents the current state of the
	 *            BlackJack game
	 */
	@Override
	public void splitLeftPlayerStands(Round round) {
		if (round == null) {
			throw new IllegalArgumentException("Invalid Value " + round);//fail fast			
		}
		
		boolean isSplitLeft = true;
		boolean splitLeftFinishedDrawingCards = true;
		round.getSplitHand().calculateSplitHandValues(
				round.getSplitHand().getSplitLeftCards(),
				splitLeftFinishedDrawingCards , isSplitLeft);
	}

	/**
	 * Deal a single card to the player's hand on the right of the client screen.
	 * Check to see if the player has gone bust.
	 * 
	 * @param round
	 *            - The round object represents the current state of the
	 *            BlackJack game
	 */
	@Override
	public void splitRightHitPlayer(Round round) {
		if (round == null) {
			throw new IllegalArgumentException("Invalid Value " + round);//fail fast			
		}
		
		boolean isSplitLeft = false;
		boolean splitRightFinishedDrawingCards = false;
		boolean bust = false;
		round.getSplitHand().getSplitRightCards().add(deck.dealRandomCard());
		bust = round.getSplitHand().checkIfSplitHandBust(
				round.getSplitHand().getSplitRightCards(), isSplitLeft);
		if (bust) {
			round.setPlayerCredits(round.getPlayerCredits()
					- round.getPlayerBet());
			round.checkIfPlayerLowOnCredits();
		}
		round.getSplitHand().calculateSplitHandValues(
				round.getSplitHand().getSplitRightCards(),
				splitRightFinishedDrawingCards , isSplitLeft);
	}

	/**
	 * The player has finished adding cards to the hand on the right of the client 
	 * screen. Calculate the hand value for this hand converting aces to 11 if that
	 * does not cause the hand to go bust. Deal cards to the dealer until the dealer 
	 * either goes bust or reaches a hand value between 17 and 21 (inclusive).
	 * Check if the player's active hands win against the dealer's hand. 
	 * 
	 * @param round
	 *            - The round object represents the current state of the
	 *            BlackJack game
	 */
	@Override
	public void splitRightPlayerStands(Round round) {
		if (round == null) {
			throw new IllegalArgumentException("Invalid Value " + round);//fail fast			
		}
		
		boolean isSplit = true;
		boolean isSplitLeft = false;
		boolean splitRightFinishedDrawingCards = true;
		round.getSplitHand().calculateSplitHandValues(
				round.getSplitHand().getSplitRightCards(),
				splitRightFinishedDrawingCards , isSplitLeft);
		
		
		// Deal cards to the dealer until the dealer either goes bust or stands
		while (!round.dealerMustStand()) {
			// Card card = deck.dealRandomCard();
			// round.getDealerCards().add(card);
			round.getDealerCards().add(deck.dealRandomCard());

			if (round.checkBust(round.getDealerCards(), false, isSplit)) {
				// This will calculate the dealer's hand value for display to the screen
				// Note that it will also calculate the value of the original player's hand but
				// this is ignored.
				round.calculateHandValues(true);
				
				return;
			}
		}

		// This will calculate the dealer's hand value for display to the screen
		// Note that it will also calculate the value of the original player's hand but
		// this is ignored.
		round.calculateHandValues(true);
		
		// The dealer stands. Check who won.
		round.checkWhoWonAfterSplit();
	}

}
