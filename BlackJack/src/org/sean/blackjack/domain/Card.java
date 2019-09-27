package org.sean.blackjack.domain;

/**
 * A playing card has a suit and a rank. For example, the Queen of Spades has a
 * suit of Spades and a rank of Queen. There are no setters, which improves immutability.
 * 
 */
public final class Card {
 
	private final Suit suit;
	private final Rank rank;

	public Rank getRank() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}

	public Card(Suit suit, Rank rank) {
		super();
		this.rank = rank;
		this.suit = suit;
	}

}
