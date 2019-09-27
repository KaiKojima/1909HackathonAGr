package org.sean.blackjack.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DeckTest {


	@Test
	public void testGetInstance() {
		int numberOfCardsInDeck=52;
		List<Card> uniqueCardsInDeck = new ArrayList<Card>();
		Deck deck = new Deck();
		//Demonstrate that there are 52 unique cards in a deck.
		for (int i = 0; i < numberOfCardsInDeck; i++){
			uniqueCardsInDeck.add(deck.dealRandomCard());
		}
		assertEquals( numberOfCardsInDeck, uniqueCardsInDeck.size() );
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void negativeTestGetInstance() {
		int numberOfCardsInDeck=52;
		Deck deck = new Deck();
		// Demonstrate that trying to deal more than 52 cards from a deck
		// will throw an IllegalArgumentException.
		for (int i = 0; i < numberOfCardsInDeck + 1; i++){
			deck.dealRandomCard();
		}
	}	

	@Test
	public void testDealRandomCard() {
		Deck deck = new Deck();
		assertEquals( (new Card(Suit.SPADES, Rank.ACE)).getClass(), deck.dealRandomCard().getClass() );
	}
	
	@Test
	public void testRandomInRange() {
		int minNumber = 0;
		int maxNumber = 51;
		int randomNumber = Deck.randomInRange(minNumber, maxNumber);
		for (int i=0; i < 1000; i++){
			assertTrue( (randomNumber >= minNumber) && (randomNumber <= maxNumber) );
		}
	}

}
