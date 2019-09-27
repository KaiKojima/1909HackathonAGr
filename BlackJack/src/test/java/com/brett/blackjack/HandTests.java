package com.brett.blackjack;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HandTests {

    @Test
    public void testAddCard(){
        Hand testHand = new Hand();
        testHand.addCard(new Card(Suit.CLUB, Value.TEN));
        assertTrue(testHand.getTotals().size() == 1);
        assertTrue(testHand.getTotals().get(0) == 10);

        List<Card> cards = testHand.getCards();
        assertTrue(cards.size() == 1);
        assertTrue(cards.get(0).getSuit().equals(Suit.CLUB));
        assertTrue(cards.get(0).getIntValue().equals(Value.TEN));

        testHand.addCard(new Card(Suit.SPADE, Value.ACE));
        assertTrue(testHand.getTotals().size() == 2);
        assertTrue(testHand.getTotals().contains(11));
        assertTrue(testHand.getTotals().contains(21));

    }

    @Test
     public void testResultBlackjack(){
        Hand testHand = new Hand();
        testHand.addCard(new Card(Suit.SPADE, Value.ACE));
        testHand.addCard(new Card(Suit.SPADE, Value.TEN));
        assertTrue(testHand.result().equals("Blackjack!"));
        assertTrue(testHand.blackJack());
    }

    @Test
    public void testTwentyOneNotBlackjack(){
        Hand testHand = new Hand();
        testHand.addCard(new Card(Suit.SPADE, Value.ACE));
        testHand.addCard(new Card(Suit.SPADE, Value.TEN));
        testHand.addCard(new Card(Suit.CLUB, Value.TEN));
        assertTrue(testHand.result().equals(""));
        assertFalse(testHand.blackJack());
    }

    @Test
    public void testBusted(){
        Hand testHand = new Hand();
        testHand.addCard(new Card(Suit.SPADE, Value.FIVE));
        testHand.addCard(new Card(Suit.SPADE, Value.TEN));
        testHand.addCard(new Card(Suit.SPADE, Value.TEN));
        assertTrue(testHand.result().equals("Busted!"));
    }



}
