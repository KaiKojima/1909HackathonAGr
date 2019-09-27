package com.brett.blackjack;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private List<Card> cards;
    private List<Integer> totals;

    public Hand() {
        totals = new ArrayList<Integer>();
        totals.add(0);

        cards = new ArrayList<Card>();
    }

    public void addCard(Card card) {
        cards.add(card);

        if (card.getIntValue().equals(Value.ACE)) {
            //increment each total by value
            List<Integer> newTotals = new ArrayList<Integer>();
            for (Integer oldTotal : totals) {
                newTotals.add(oldTotal + 1);
                newTotals.add(oldTotal + 11);
            }
            totals = newTotals;
        } else {
            //increment each total by value
            List<Integer> newTotals = new ArrayList<Integer>();
            for (Integer oldTotal : totals) {
                newTotals.add(oldTotal + card.getIntValue().value());
            }
            totals = newTotals;
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card.toString());
        }
        return sb.toString();
    }

    public int size() {
        return cards.size();
    }

    public List<Integer> getTotals() {
        return totals;
    }

    public String result() {
        return blackJack() ? "Blackjack!" : finalTotal() > 21 ? "Busted!" : "";
    }

    public boolean blackJack() {
        return finalTotal().equals(21) && size() == 2;
    }

    //Largest total less than 21 or smallest total if over 21..
    public Integer finalTotal() {
        Integer currFinalTotal = totals.get(0);
        for (Integer total : totals) {
            if ((total > currFinalTotal && total <= 21) || ((total < currFinalTotal) && bothGreaterThanTwentyOne(total, currFinalTotal))) {
                currFinalTotal = total;
            }
        }
        return currFinalTotal;
    }

    private boolean bothGreaterThanTwentyOne(Integer total, int finalTotal) {
        return (total > 21 && finalTotal > 21);
    }

}
