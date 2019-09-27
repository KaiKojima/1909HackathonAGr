package org.sean.blackjack.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * The SplitPlayer class describes the player's two active hands when the player
 * enables the split card feature during a BlackJack hand. This feature is only
 * available when the player is dealt two cards of equal hand value.
 * 
 */
public final class SplitHand {
	
	private final List<Card> splitLeftCards = new ArrayList<Card>();
	private final List<Card> splitRightCards = new ArrayList<Card>();
	private String splitLeftGameMessage = Consts.BLANK_MESSAGE;
	private String splitRightGameMessage = Consts.BLANK_MESSAGE;
	private String splitLeftHandValue = Consts.BLANK_MESSAGE;
	private String splitRightHandValue = Consts.BLANK_MESSAGE;
	private boolean splitLeftBust;
	private boolean splitRightBust;

	/**
	 * Check that the value of the hand has not gone above 21. For the purposes
	 * of this check, an ace will always have a value of 1. If the player is
	 * bust, deduct the player's bet from the player's credits.
	 * 
	 * @param cards
	 *            - the list of cards to be checked
	 * @param isSplitLeft
	 *            - true if this is the player's left hand split, false if it is
	 *            the right hand split
	 * @return boolean - true if the cards are over 21
	 */
	public boolean checkIfSplitHandBust(List<Card> cards, boolean isSplitLeft) {
		if (cards == null) {
			throw new IllegalArgumentException("Invalid Hand of Cards " + cards);//fail fast			
		}
		
		// The total value of cards allowed in a hand of blackjack
		// is 21.
		int total = 0;
		for (Card card : cards) {
			total = total + card.getRank().getCardValue();
		}
		if (isSplitLeft) {
			if (total > Consts.TWENTY_ONE) {
				this.splitLeftBust = true;
				this.splitLeftGameMessage = Consts.PLAYER_BUST;
				return true;
			}
		} else {
			if (total > Consts.TWENTY_ONE) {
				this.splitRightBust = true;
				this.splitRightGameMessage = Consts.PLAYER_BUST;
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * Calculate the value of the cards passed to the method. Adjust for aces.
	 * 
	 * @param cards
	 *            - the list of cards to be checked
	 * @param playerFinishedDrawingCards
	 *            - true if the player has gone bust or has pressed the stand
	 *            button, false otherwise.
	 * @param isSplitLeft
	 *            - true if this is the player's left hand split, false if it is
	 *            the right hand split
	 */
	public void calculateSplitHandValues(List<Card> cards,
			boolean playerFinishedDrawingCards, boolean isSplitLeft) {
		if (cards == null) {
			throw new IllegalArgumentException("Invalid Hand of Cards " + cards);//fail fast			
		}
		
		// The boolean playerFinishedDrawingCards indicates that the player has
		// received all
		// their cards. In this case, the Ace will be set to a fixed value of
		// either 1 or 11.
		// boolean playerFinishedDrawingCards = thePlayerIsFinishedDrawingCards;
		int total = 0;

		int numberOfPlayersAces = 0;

		for (Card card : cards) {
			total = total + card.getRank().getCardValue();
			numberOfPlayersAces = ifAceThenIncrementAceCount(
					numberOfPlayersAces, card);
		}

		if (isSplitLeft) {
			this.splitLeftHandValue = this.calculateHandValue(
					playerFinishedDrawingCards, total, numberOfPlayersAces);
		} else {
			this.splitRightHandValue = this.calculateHandValue(
					playerFinishedDrawingCards, total, numberOfPlayersAces);
		}
	}

	/**
	 * 
	 * Calculate the string representation, adjusted for aces, of the value of
	 * the cards passed to the method.
	 * 
	 * @param playerFinishedDrawingCards
	 *            - true if the player has gone bust or has pressed the stand
	 *            button, false otherwise.
	 * @param total
	 *            - the value of the hand before adjusting for aces
	 * @param numberOfAces
	 *            - the number of aces
	 * @return handValue - the String representation of the value of the hand,
	 *         allowing for aces
	 */
	private String calculateHandValue(boolean playerFinishedDrawingCards,
			int total, int numberOfAces) {
		String handValue;
		handValue = String.valueOf(total);
		if ((total <= 11) && numberOfAces != 0) {
			if (playerFinishedDrawingCards) {
				handValue = String.valueOf(total + 10);
			} else {
				handValue = handValue + " or " + String.valueOf(total + 10);
			}
		}
		return handValue;
	}

	/**
	 * If the card passed to the method is an ace, increment and return the
	 * numberOfAces count.
	 * 
	 * @param numberOfAces
	 *            - the number of aces
	 * @param card
	 *            - the card to be checked if an ace or not
	 * @return numberOfAces - the number of aces
	 */
	private int ifAceThenIncrementAceCount(int numberOfAces, Card card) {
		if ((card.getRank().getCardValue() == 1)) {
			numberOfAces++;
		}
		return numberOfAces;
	}
	//Not referred to by any code. Candidate for removal.
	public String getSplitLeftGameMessage() {
		return splitLeftGameMessage;
	}

	public void setSplitLeftGameMessage(String splitLeftGameMessage) {
		if (splitLeftGameMessage == null) {
			throw new IllegalArgumentException("Invalid String " + splitLeftGameMessage);//fail fast			
		}
		
		this.splitLeftGameMessage = splitLeftGameMessage;
	}
	//Not referred to by any code. Candidate for removal.
	public String getSplitRightGameMessage() {
		return splitRightGameMessage;
	}

	public void setSplitRightGameMessage(String splitRightGameMessage) {
		if (splitRightGameMessage == null) {
			throw new IllegalArgumentException("Invalid String " + splitRightGameMessage);//fail fast			
		}
		
		this.splitRightGameMessage = splitRightGameMessage;
	}

	public boolean isSplitLeftBust() {
		return splitLeftBust;
	}
	//Not referred to by any code. Candidate for removal.
	public void setSplitLeftBust(boolean splitLeftBust) {
		this.splitLeftBust = splitLeftBust;
	}

	public boolean isSplitRightBust() {
		return splitRightBust;
	}
	//Not referred to by any code. Candidate for removal.
	public void setSplitRightBust(boolean splitRightBust) {
		this.splitRightBust = splitRightBust;
	}
	//Not referred to by any code. Candidate for removal.
	public String getSplitLeftHandValue() {
		return splitLeftHandValue;
	}
	//Not referred to by any code. Candidate for removal.
	public void setSplitLeftHandValue(String splitLeftHandValue) {
		if (splitLeftHandValue == null) {
			throw new IllegalArgumentException("Invalid String " + splitLeftHandValue);//fail fast			
		}
		
		this.splitLeftHandValue = splitLeftHandValue;
	}
	//Not referred to by any code. Candidate for removal.
	public String getSplitRightHandValue() {
		return splitRightHandValue;
	}
	//Not referred to by any code. Candidate for removal.
	public void setSplitRightHandValue(String splitRightHandValue) {
		if (splitRightHandValue == null) {
			throw new IllegalArgumentException("Invalid String " + splitRightHandValue);//fail fast			
		}
		
		this.splitRightHandValue = splitRightHandValue;
	}

	public List<Card> getSplitLeftCards() {
		return splitLeftCards;
	}

	public List<Card> getSplitRightCards() {
		return splitRightCards;
	}

}