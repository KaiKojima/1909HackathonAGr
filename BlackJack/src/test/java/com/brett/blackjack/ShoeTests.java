package com.brett.blackjack;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ShoeTests {

    @Test
    public void testConstructor(){
        Shoe shoe = new Shoe(6);
        assertTrue(shoe.getDeckCount() == 6);
        assertTrue(shoe.getCardCount() == shoe.getDeckCount() * 52);
    }

    @Test
    public void testDraw(){
        Shoe shoe = new Shoe(6);
        assertTrue(shoe.draw() instanceof Card);
        assertTrue(shoe.getCardCount() == shoe.getDeckCount() * 52 - 1);

    }


}
