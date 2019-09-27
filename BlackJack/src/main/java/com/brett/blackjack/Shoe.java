package com.brett.blackjack;

import java.util.List;
import java.util.Stack;

public class Shoe {

    private int deckCount;
    private Stack<Card> cards;

    public Shoe(int numDecks){

        this.deckCount = numDecks;

        cards = new Stack<Card>();
        for(int i=0;i<numDecks;i++){
            Deck currDeck = new Deck();
            currDeck.shuffle();
            cards.addAll(currDeck.getCards());
        }
    }

    public int getDeckCount(){
        return deckCount;
    }

    public int getCardCount() {
        return cards.size();
    }

    public Card draw(){
        return cards.pop();
    }
}
