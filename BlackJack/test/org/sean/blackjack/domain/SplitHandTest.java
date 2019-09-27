package org.sean.blackjack.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.sean.blackjack.services.BlackJackServiceImpl;

public class SplitHandTest {
	
	BlackJackServiceImpl blackJackServiceImpl = null;
	Round round = null;
	
	@Before
	 public void init() {
		blackJackServiceImpl = new BlackJackServiceImpl();
		round = Round.getInstance();
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		round.getDealerCards().clear();
		round.getSplitHand().getSplitLeftCards().clear();
		round.getSplitHand().getSplitRightCards().clear();
	 }

	@Test
	public void test1CheckSplitBust() {
		boolean isSplitLeft = true;
		List<Card> cards = new ArrayList<Card>();

		round.getSplitHand().getSplitLeftCards().add(new Card(Suit.DIAMONDS, Rank.TEN));
		round.getSplitHand().getSplitLeftCards().add(new Card(Suit.DIAMONDS, Rank.SIX));		
		cards = round.getSplitHand().getSplitLeftCards();
		round.getSplitHand().checkIfSplitHandBust(cards, isSplitLeft);
		assertFalse(round.getSplitHand().isSplitLeftBust());		
	}
	
	@Test
	public void test2CheckSplitBust() {
		boolean isSplitLeft = true;
		List<Card> cards = new ArrayList<Card>();

		round.getSplitHand().getSplitLeftCards().add(new Card(Suit.DIAMONDS, Rank.TEN));
		round.getSplitHand().getSplitLeftCards().add(new Card(Suit.DIAMONDS, Rank.SIX));		
		round.getSplitHand().getSplitLeftCards().add(new Card(Suit.HEARTS, Rank.SIX));
		cards = round.getSplitHand().getSplitLeftCards();		
		round.getSplitHand().checkIfSplitHandBust(cards, isSplitLeft);
		assertTrue(round.getSplitHand().isSplitLeftBust());	
	}
	
	@Test
	public void test3CheckSplitBust() {
		boolean isSplitLeft = false;
		List<Card> cards = new ArrayList<Card>();

		round.getSplitHand().getSplitRightCards().add(new Card(Suit.DIAMONDS, Rank.TEN));
		round.getSplitHand().getSplitRightCards().add(new Card(Suit.DIAMONDS, Rank.SIX));
		cards = round.getSplitHand().getSplitRightCards();
		round.getSplitHand().checkIfSplitHandBust(cards, isSplitLeft);	
		assertFalse(round.getSplitHand().isSplitRightBust());		
	}
	
	@Test
	public void test4CheckSplitBust() {
		boolean isSplitLeft = false;
		List<Card> cards = new ArrayList<Card>();

		round.getSplitHand().getSplitRightCards().add(new Card(Suit.DIAMONDS, Rank.TEN));
		round.getSplitHand().getSplitRightCards().add(new Card(Suit.DIAMONDS, Rank.SIX));
		round.getSplitHand().getSplitRightCards().add(new Card(Suit.HEARTS, Rank.SIX));
		cards = round.getSplitHand().getSplitRightCards();
		round.getSplitHand().checkIfSplitHandBust(cards, isSplitLeft);	
		assertTrue(round.getSplitHand().isSplitRightBust());		
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestCheckSplitBust() {
		new SplitHand().checkIfSplitHandBust(null, false);
	}

	@Test
	public void test1CalculateSplitHandValues() {
		boolean playerFinishedDrawingCards = true;
		boolean isSplitLeft = true;
		List<Card> cards = new ArrayList<Card>();
		
		round.getSplitHand().getSplitLeftCards().add(new Card(Suit.DIAMONDS, Rank.TEN));
		round.getSplitHand().getSplitLeftCards().add(new Card(Suit.DIAMONDS, Rank.SIX));		
		cards = round.getSplitHand().getSplitLeftCards();
		round.getSplitHand().calculateSplitHandValues(cards, playerFinishedDrawingCards, isSplitLeft);
		assertEquals( "16", round.getSplitHand().getSplitLeftHandValue() );
	}
	
	@Test
	public void test2CalculateSplitHandValues() {
		boolean playerFinishedDrawingCards = false;
		boolean isSplitLeft = false;
		List<Card> cards = new ArrayList<Card>();
		
		round.getSplitHand().getSplitRightCards().add(new Card(Suit.CLUBS, Rank.TEN));
		round.getSplitHand().getSplitRightCards().add(new Card(Suit.CLUBS, Rank.ACE));		
		cards = round.getSplitHand().getSplitRightCards();
		round.getSplitHand().calculateSplitHandValues(cards, playerFinishedDrawingCards, isSplitLeft);
		assertEquals( "11 or 21", round.getSplitHand().getSplitRightHandValue() );
	}
	
	@Test
	public void test3CalculateSplitHandValues() {
		boolean playerFinishedDrawingCards = true;
		boolean isSplitLeft = false;
		List<Card> cards = new ArrayList<Card>();
		
		round.getSplitHand().getSplitRightCards().add(new Card(Suit.CLUBS, Rank.TEN));
		round.getSplitHand().getSplitRightCards().add(new Card(Suit.CLUBS, Rank.ACE));		
		cards = round.getSplitHand().getSplitRightCards();
		round.getSplitHand().calculateSplitHandValues(cards, playerFinishedDrawingCards, isSplitLeft);
		assertEquals( "21", round.getSplitHand().getSplitRightHandValue() );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestCalculateSplitHandValues() {
		new SplitHand().calculateSplitHandValues(null, false, false);
	}

	@Test
	public void testGetSplitLeftGameMessage() {
		round.getSplitHand().setSplitLeftGameMessage(Consts.DEALER_BUST);
		assertEquals(Consts.DEALER_BUST, round.getSplitHand().getSplitLeftGameMessage());
	}

	@Test
	public void testSetSplitLeftGameMessage() {
		round.getSplitHand().setSplitLeftGameMessage(Consts.DEALER_BUST);
		assertEquals(Consts.DEALER_BUST, round.getSplitHand().getSplitLeftGameMessage());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestSetSplitLeftGameMessage() {
		new SplitHand().setSplitLeftGameMessage(null);
	}

	@Test
	public void testGetSplitRightGameMessage() {
		round.getSplitHand().setSplitRightGameMessage(Consts.DEALER_BUST);
		assertEquals(Consts.DEALER_BUST, round.getSplitHand().getSplitRightGameMessage());
	}

	@Test
	public void testSetSplitRightGameMessage() {
		round.getSplitHand().setSplitRightGameMessage(Consts.DEALER_BUST);
		assertEquals(Consts.DEALER_BUST, round.getSplitHand().getSplitRightGameMessage());		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestSetSplitRightGameMessage() {
		new SplitHand().setSplitRightGameMessage(null);
	}

	@Test
	public void testIsSplitLeftBust() {
		round.getSplitHand().setSplitLeftBust(true);
		assertTrue(round.getSplitHand().isSplitLeftBust());
	}

	@Test
	public void testSetSplitLeftBust() {
		round.getSplitHand().setSplitLeftBust(true);
		assertTrue(round.getSplitHand().isSplitLeftBust());
	}

	@Test
	public void testIsSplitRightBust() {
		round.getSplitHand().setSplitRightBust(true);
		assertTrue(round.getSplitHand().isSplitRightBust());
	}

	@Test
	public void testSetSplitRightBust() {
		round.getSplitHand().setSplitRightBust(true);
		assertTrue(round.getSplitHand().isSplitRightBust());
	}

	@Test
	public void testGetSplitLeftHandValue() {	
		round.getSplitHand().setSplitLeftHandValue("1 or 11");
		assertEquals("1 or 11", round.getSplitHand().getSplitLeftHandValue());
	}

	@Test
	public void testSetSplitLeftHandValue() {	
		round.getSplitHand().setSplitLeftHandValue("1 or 11");
		assertEquals("1 or 11", round.getSplitHand().getSplitLeftHandValue());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestSetSplitLeftHandValue() {
		new SplitHand().setSplitLeftHandValue(null);
	}

	@Test
	public void testGetSplitRightHandValue() {		
		round.getSplitHand().setSplitRightHandValue("1 or 11");
		assertEquals("1 or 11", round.getSplitHand().getSplitRightHandValue());
	}

	@Test
	public void testSetSplitRightHandValue() {		
		round.getSplitHand().setSplitRightHandValue("1 or 11");
		assertEquals("1 or 11", round.getSplitHand().getSplitRightHandValue());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestSetSplitRightHandValue() {
		new SplitHand().setSplitRightHandValue(null);
	}

	@Test
	public void testGetSplitLeftCards() {
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		assertEquals( 1, round.getSplitHand().getSplitLeftCards().size() );
	}

	@Test
	public void testGetSplitRightCards() {
		blackJackServiceImpl.startRound(round);
		blackJackServiceImpl.playerSplits(round);
		assertEquals( 1, round.getSplitHand().getSplitRightCards().size() );
	}

}
