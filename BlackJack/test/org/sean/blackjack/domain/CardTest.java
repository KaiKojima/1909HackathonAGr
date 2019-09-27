package org.sean.blackjack.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class CardTest {

	@Test
	public void testGetRank() {
		Card card = new Card(Suit.CLUBS, Rank.ACE);
		assertEquals(Rank.ACE, card.getRank());
	}

	@Test
	public void testGetSuit() {
		Card card = new Card(Suit.CLUBS, Rank.ACE);
		assertEquals(Suit.CLUBS, card.getSuit());
	}

	@Test
	public void test1CardSuitRank() {
		Card card = new Card(Suit.HEARTS, Rank.ACE);
		assertEquals(Suit.HEARTS, card.getSuit());
	}
	
	@Test
	public void test2CardSuitRank() {
		Card card = new Card(Suit.HEARTS, Rank.ACE);
		assertEquals(Rank.ACE, card.getRank());
	}

}
