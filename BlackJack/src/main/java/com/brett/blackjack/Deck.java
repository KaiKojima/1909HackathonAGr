package com.brett.blackjack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

public class Deck {

    private Stack<Card> cards;

    public Deck() {
        cards = new Stack<Card>();
        for (Suit suit : Suit.values()) {
            for (Value value : Value.values()) {
                Card currCard = new Card(suit, value);
                cards.add(currCard);
            }
        }
    }

    public Collection<? extends Card> getCards() {
        return cards;
    }

    public void shuffle() {
        List<Card> cardsToShuffle = new ArrayList<Card>();
        for (Card c : cards) {
            cardsToShuffle.add(c);
        }

        this.cards = new Stack<Card>();
        int count = cardsToShuffle.size();
        for (int i = 0; i < count; i++) {
            int randomCardNumber = (int) (Math.random() * cardsToShuffle.size());
            cards.push(cardsToShuffle.remove(randomCardNumber));
        }
    }


    public Integer getCount() {
        return cards.size();
    }
}
