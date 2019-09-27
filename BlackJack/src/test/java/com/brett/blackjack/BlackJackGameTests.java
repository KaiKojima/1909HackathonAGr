package com.brett.blackjack;

import com.brett.blackjack.DealerStrategies.DealerHitSoftSeventeen;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BlackJackGameTests {

    @Test
    public void testConstructor() {
        BlackJackGame game = new BlackJackGame();
        assertTrue(game.getDealersHand().size() == 1);
        assertTrue(game.getPlayersHand().size() == 2);
    }

    @Test
    public void testHit() {
        BlackJackGame game = new BlackJackGame();
        game.playerHit();
        assertTrue(game.getPlayersHand().size() == 3);
    }

    @Test
    public void testDealerHitSoftSeventeen() {
        BlackJackGame game = new BlackJackGame();
        Hand dealersHand = new Hand();
        dealersHand.addCard(new Card(Suit.CLUB, Value.ACE));
        dealersHand.addCard(new Card(Suit.CLUB, Value.SIX));
        game.setDealersHand(dealersHand);

        DealerHitSoftSeventeen resolver = new DealerHitSoftSeventeen(game);
        resolver.resolveDealerHand();

        assertTrue(dealersHand.size() > 2);

        boolean totalBelowSeventeenFound = false;
        for (Integer total : game.getDealersHand().getTotals()) {
            if (total < 17) {
                totalBelowSeventeenFound = true;
            }
        }
        assertFalse(totalBelowSeventeenFound);
    }

    @Test
    public void testDealerBlackjackWins() {
        BlackJackGame game = new BlackJackGame();

        Hand dealersHand = new Hand();
        dealersHand.addCard(new Card(Suit.SPADE, Value.ACE));
        dealersHand.addCard(new Card(Suit.SPADE, Value.QUEEN));
        game.setDealersHand(dealersHand);

        Hand playersHand = new Hand();
        playersHand.addCard(new Card(Suit.SPADE, Value.ACE));
        playersHand.addCard(new Card(Suit.SPADE, Value.QUEEN));
        game.setPlayersHand(playersHand);

        assertTrue(game.result().equals("You lose! :("));
    }

    @Test
    public void testPlayerBlackjackBeatsTwentyOne() {
        BlackJackGame game = new BlackJackGame();

        Hand dealersHand = new Hand();
        dealersHand.addCard(new Card(Suit.SPADE, Value.ACE));
        dealersHand.addCard(new Card(Suit.SPADE, Value.FIVE));
        dealersHand.addCard(new Card(Suit.CLUB, Value.FIVE));
        game.setDealersHand(dealersHand);

        Hand playersHand = new Hand();
        playersHand.addCard(new Card(Suit.SPADE, Value.ACE));
        playersHand.addCard(new Card(Suit.SPADE, Value.QUEEN));
        game.setPlayersHand(playersHand);

        assertTrue(game.result().equals("You win!"));
    }

    @Test
    public void testTwentyOnesPush() {
        BlackJackGame game = new BlackJackGame();

        Hand dealersHand = new Hand();
        dealersHand.addCard(new Card(Suit.SPADE, Value.ACE));
        dealersHand.addCard(new Card(Suit.SPADE, Value.FIVE));
        dealersHand.addCard(new Card(Suit.CLUB, Value.FIVE));
        game.setDealersHand(dealersHand);

        Hand playersHand = new Hand();
        playersHand.addCard(new Card(Suit.SPADE, Value.ACE));
        playersHand.addCard(new Card(Suit.SPADE, Value.FIVE));
        playersHand.addCard(new Card(Suit.SPADE, Value.FIVE));
        game.setPlayersHand(playersHand);

        assertTrue(game.result().equals("Push"));
    }

    @Test
    public void dealerStandsHardSeventeen() {
        BlackJackGame game = new BlackJackGame();

        Hand dealersHand = new Hand();
        dealersHand.addCard(new Card(Suit.SPADE, Value.SEVEN));
        dealersHand.addCard(new Card(Suit.SPADE, Value.FIVE));
        dealersHand.addCard(new Card(Suit.CLUB, Value.FIVE));
        game.setDealersHand(dealersHand);

        game.resolveDealerHand();
        assertTrue(dealersHand.size() == 3);
    }

    @Test
    public void dealerStandsSoftSeventeen(){
        BlackJackGame game = new BlackJackGame();

        Hand dealersHand = new Hand();
        dealersHand.addCard(new Card(Suit.SPADE, Value.SIX));
        dealersHand.addCard(new Card(Suit.SPADE, Value.ACE));
        game.setDealersHand(dealersHand);

        game.resolveDealerHand();
        assertTrue(dealersHand.size() == 2);
    }

    @Test
    public void dealerHitSixteen(){
        BlackJackGame game = new BlackJackGame();

        Hand dealersHand = new Hand();
        dealersHand.addCard(new Card(Suit.SPADE, Value.FOUR));
        dealersHand.addCard(new Card(Suit.SPADE, Value.TWO));
        dealersHand.addCard(new Card(Suit.SPADE, Value.NINE));
        dealersHand.addCard(new Card(Suit.SPADE, Value.ACE));
        game.setDealersHand(dealersHand);

        game.resolveDealerHand();
        assertTrue(dealersHand.size() == 5);
    }

}
