package org.sean.blackjack.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Deck creates a 52 card deck based on the fields of the enums, Suit and Rank.
 * The 52 cards are stored in a Collection of cards. When a random card is dealt
 * from the deck, then that card is removed from the Collection.
 * 
 */
public final class Deck {
	// Note that the Collection of cards, cardsInDeck is well encapsulated and cannot be accessed
	// outside the Deck class. Only the "top card in the deck" can be accessed
	// via the dealRandomCard() method.
	private final List<Card> cardsInDeck = new ArrayList<Card>();

	/**
	 * Add 52 cards to the new deck.
	 */
	public Deck() {
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				cardsInDeck.add(new Card(suit, rank));
			}
		}
	}

	/**
	 * Randomly select a card from the remaining cards in the deck. This card
	 * is removed from the deck and returned by the method.
	 * 
	 * @return card - a random card from the deck.
	 */
	public Card dealRandomCard() {
		Card card = this.cardsInDeck.get(Deck.randomInRange(0,
				this.cardsInDeck.size() - 1));
		this.cardsInDeck.remove(card);
		return card;
	}

	/**
	 * Generate a random integer between min (inclusive) and max (inclusive).
	 * For the full deck of cards, set min = 0 and max = 51 to generate a random
	 * int from 52 possible values.
	 * 
	 * @param min
	 *            normally set to zero
	 * @param max
	 *            normally set to "size of the deck minus one"
	 * @return randomNum - a random integer in the specified range
	 */
	static int randomInRange(int min, int max) {
		if (min > max) {
			throw new IllegalArgumentException(
					"Random number range is invalid.");
		}
		Random random = new Random();
		int randomNum = random.nextInt(max - min + 1) + min;
		return randomNum;
	}

}
