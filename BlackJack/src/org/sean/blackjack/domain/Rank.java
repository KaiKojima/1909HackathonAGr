package org.sean.blackjack.domain;

/**
 * Rank is an enum of playing card ranks.
 * 
 * Each rank has a value associated with it. This is the value that the rank has
 * in the card game, Blackjack. An Ace actually has two possible values, 1 and
 * 11. Only the value 1 is assigned to the Ace here. The value 11 is assigned to
 * the Ace by methods in the Round and SplitHand classes when appropriate.
 * 
 */
public enum Rank {
	ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(
			9), TEN(10), JACK(10), QUEEN(10), KING(10);

	private final int cardValue;

	Rank(int cardValue) {
		this.cardValue = cardValue;
	}

	public int getCardValue() {
		return cardValue;
	}
}
