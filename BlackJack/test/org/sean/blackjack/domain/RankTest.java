package org.sean.blackjack.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class RankTest {



	@Test
	public void testGetCardValue() {
		assertEquals(1, Rank.ACE.getCardValue());
		assertEquals(2, Rank.TWO.getCardValue());
		assertEquals(3, Rank.THREE.getCardValue());
		assertEquals(4, Rank.FOUR.getCardValue());
		assertEquals(5, Rank.FIVE.getCardValue());
		assertEquals(6, Rank.SIX.getCardValue());
		assertEquals(7, Rank.SEVEN.getCardValue());
		assertEquals(8, Rank.EIGHT.getCardValue());
		assertEquals(9, Rank.NINE.getCardValue());
		assertEquals(10, Rank.TEN.getCardValue());
		assertEquals(10, Rank.JACK.getCardValue());
		assertEquals(10, Rank.QUEEN.getCardValue());
		assertEquals(10, Rank.KING.getCardValue());
	}

}
