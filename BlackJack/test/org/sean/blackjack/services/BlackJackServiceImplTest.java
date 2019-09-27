package org.sean.blackjack.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.sean.blackjack.domain.Card;
import org.sean.blackjack.domain.Consts;
import org.sean.blackjack.domain.Rank;
import org.sean.blackjack.domain.Round;
import org.sean.blackjack.domain.Suit;

public class BlackJackServiceImplTest {
	
	Round round;
	BlackJackService blackJackService;
	
	@Before
	public void init(){
		round = Round.getInstance();
		blackJackService = new BlackJackServiceImpl();
		blackJackService.initializeTable(round);
		round.getPlayerCards().clear();
		round.getDealerCards().clear();
	}

	@Test
	public void test1InitializeTable() {
		assertEquals(Consts.STARTING_CREDITS, round.getPlayerCredits(), 0);

	}
	
	@Test
	public void test2InitializeTable() {
		assertEquals(Consts.STARTING_BET, round.getPlayerBet());
	}

	@Test(expected=IllegalArgumentException.class)
	public void negativeTestInitializeTable() {
		new BlackJackServiceImpl().initializeTable(null);
	}
	
	@Test
	public void test1StartRound() {
		blackJackService.startRound(round);
		assertEquals(Consts.BLANK_MESSAGE, round.getGameMessage());
	}
	
	@Test
	public void test2StartRound() {
		int indexOfFirstCardInList = 0;
		blackJackService.startRound(round);
		// Test that the objects in the playerCards are Cards
		assertEquals(new Card(Suit.SPADES, Rank.ACE).getClass(),
				round.getDealerCards().get(indexOfFirstCardInList).getClass());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestStartRound() {
		new BlackJackServiceImpl().startRound(null);
	}

	@Test
	public void testHitPlayer() {
		int numberOfPlayerCardsBeforeHit = 2;
		int numberOfPlayerCardsPerHit = 1;
		blackJackService.startRound(round);
		blackJackService.hitPlayer(round);
		assertEquals(numberOfPlayerCardsBeforeHit + numberOfPlayerCardsPerHit,
				round.getPlayerCards().size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestHitPlayer() {
		new BlackJackServiceImpl().hitPlayer(null);
	}

	@Test
	public void test1PlayerDoubles() {
		blackJackService.startRound(round);
		blackJackService.playerDoubles(round);
		assertEquals(3, round.getPlayerCards().size());
	}
	
	@Test
	public void test2PlayerDoubles() {
		int betBeforeDouble;
		int betAfterDouble;
		blackJackService.startRound(round);
		betBeforeDouble = round.getPlayerBet();
		blackJackService.playerDoubles(round);
		betAfterDouble = round.getPlayerBet();
		assertEquals(betBeforeDouble * 2, betAfterDouble);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestPlayerDoubles() {
		new BlackJackServiceImpl().playerDoubles(null);
	}

	@Test
	public void test1PlayerStands() {
		// Necessary to call blackJackService.startRound to make a deck.
		blackJackService.startRound(round);
		round.getPlayerCards().clear();
		round.getDealerCards().clear();

		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.ACE));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.SIX));
		blackJackService.playerStands(round);
		assertTrue(round.isPlayerHasBlackJack());
	}
	
	@Test
	public void test2PlayerStands() {
		// Necessary to call blackJackService.startRound to make a deck.
		blackJackService.startRound(round);
		round.getPlayerCards().clear();
		round.getDealerCards().clear();

		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.ACE));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		round.getDealerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		blackJackService.playerStands(round);
		assertTrue(round.getDealerCards().size() != 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestPlayerStands() {
		new BlackJackServiceImpl().playerStands(null);
	}

	@Test
	public void testChangeBet() {
		String betSize = "100";
		blackJackService.changeBet(round, betSize);
		assertEquals(100, round.getPlayerBet());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTest1ChangeBet() {
		String betSize = "100";
		new BlackJackServiceImpl().changeBet(null, betSize);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTest2ChangeBet() {
		String betSize = "0";
		new BlackJackServiceImpl().changeBet(Round.getInstance(), betSize);
	}

	@Test
	public void test1PlayerSplits() {
		round.getPlayerCards().add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		blackJackService.playerSplits(round);
		assertEquals(1, round.getSplitHand().getSplitLeftCards().size());
	}
	
	@Test
	public void test2PlayerSplits() {
		round.getPlayerCards().add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		blackJackService.playerSplits(round);
		assertEquals(1, round.getSplitHand().getSplitRightCards().size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestPlayerSplits() {
		new BlackJackServiceImpl().playerSplits(null);
	}

	@Test
	public void testSplitLeftHitPlayer() {
		// Necessary to call blackJackService.startRound to make a deck.
		blackJackService.startRound(round);
		round.getPlayerCards().clear();
		
		round.getPlayerCards().add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		blackJackService.playerSplits(round);
		blackJackService.splitLeftHitPlayer(round);
		assertEquals(2, round.getSplitHand().getSplitLeftCards().size());

	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestSplitLeftHitPlayer() {
		new BlackJackServiceImpl().splitLeftHitPlayer(null);
	}

	@Test
	public void testSplitLeftPlayerStands() {
		// Necessary to call blackJackService.startRound to make a deck.
		blackJackService.startRound(round);
		blackJackService.playerSplits(round);

		round.getSplitHand().getSplitLeftCards().clear();
		round.getSplitHand().getSplitLeftCards()
				.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		round.getSplitHand().getSplitLeftCards()
				.add(new Card(Suit.HEARTS, Rank.ACE));
		blackJackService.splitLeftPlayerStands(round);
		assertEquals("21", round.getSplitHand().getSplitLeftHandValue());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestSplitLeftPlayerStands() {
		new BlackJackServiceImpl().splitLeftPlayerStands(null);
	}

	@Test
	public void test1SplitRightHitPlayer() {
		// Necessary to call blackJackService.startRound to make a deck.
		blackJackService.startRound(round);
		round.getPlayerCards().clear();
		
		// (Consts.LOW_CREDITS_VALUE - 100) chosen as it will definitely
		// be lower than the required amount for credit top up to occur.
		round.setPlayerCredits(Consts.LOW_CREDITS_VALUE - 100);
		round.getPlayerCards().add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		blackJackService.playerSplits(round);
		blackJackService.splitRightHitPlayer(round);
		assertEquals(2, round.getSplitHand().getSplitRightCards().size());
	}
	
	@Test
	public void test2SplitRightHitPlayer() {
		// Necessary to call blackJackService.startRound to make a deck.
		blackJackService.startRound(round);
		round.getPlayerCards().clear();
		
		// (Consts.LOW_CREDITS_VALUE - 100) chosen as it will definitely
		// be lower than the required amount for credit top up to occur.
		round.setPlayerCredits(Consts.LOW_CREDITS_VALUE - 100);
		round.getPlayerCards().add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		round.getPlayerCards().add(new Card(Suit.HEARTS, Rank.TEN));
		blackJackService.playerSplits(round);
//		blackJackService.splitRightHitPlayer(round);
		round.getSplitHand().getSplitRightCards()
				.add(new Card(Suit.HEARTS, Rank.JACK));
		round.getSplitHand().getSplitRightCards()
				.add(new Card(Suit.HEARTS, Rank.ACE));
		// splitRightCards now has 3 cards with a value of 21. So adding another
		// card will cause it to go bust, A check will then be run to see if the 
		// player's credits need topping up
		blackJackService.splitRightHitPlayer(round);
		assertEquals(Consts.STARTING_CREDITS, round.getPlayerCredits(), 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestSplitRightHitPlayer() {
		new BlackJackServiceImpl().splitRightHitPlayer(null);
	}

	@Test
	public void test1SplitRightPlayerStands() {
		// Necessary to call blackJackService.startRound to make a deck.
		blackJackService.startRound(round);
		blackJackService.playerSplits(round);

		round.getSplitHand().getSplitRightCards().clear();
		round.getSplitHand().getSplitRightCards()
				.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		round.getSplitHand().getSplitRightCards()
				.add(new Card(Suit.HEARTS, Rank.ACE));

		assertEquals(1, round.getDealerCards().size());
		blackJackService.splitRightPlayerStands(round);
		assertEquals("21", round.getSplitHand().getSplitRightHandValue());
	}
	
	@Test
	public void test2SplitRightPlayerStands() {
		// Necessary to call blackJackService.startRound to make a deck.
		blackJackService.startRound(round);
		blackJackService.playerSplits(round);

		round.getSplitHand().getSplitRightCards().clear();
		round.getSplitHand().getSplitRightCards()
				.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		round.getSplitHand().getSplitRightCards()
				.add(new Card(Suit.HEARTS, Rank.ACE));

		assertEquals(1, round.getDealerCards().size());
		blackJackService.splitRightPlayerStands(round);
		assertTrue(round.getDealerCards().size() > 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeTestSplitRightPlayerStands() {
		new BlackJackServiceImpl().splitRightPlayerStands(null);
	}

}
