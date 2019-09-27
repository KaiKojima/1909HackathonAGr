package com.brett.blackjack;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DeckTests {

    @Test
    public void testDeckConstructor(){
        Deck deck = new Deck();
        assertTrue(deck.getCount().equals(52));
    }

    @Test
    public void testShuffle() throws Exception {
        Deck deck = new Deck();
        deck.shuffle();
    }
}
