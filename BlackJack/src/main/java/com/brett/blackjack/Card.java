package com.brett.blackjack;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Card {

    private Value value;
    private Suit suit;

    public Card(Suit suit, Value value) {
        this.value = value;
        this.suit = suit;
    }

    public Value getIntValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    @Override
    public String toString() {
        Character value = valuePrintMap.get(this.getIntValue());
        Character suit = suitPrintMap.get(this.getSuit());

        return "[" + value + suit + "]";
    }

    private static final Map<Value,Character> valuePrintMap;
    static {
        Map<Value,Character> m = new HashMap<Value,Character>();
        m.put(Value.ACE,'A');
        m.put(Value.TWO,'2');
        m.put(Value.THREE,'3');
        m.put(Value.FOUR,'4');
        m.put(Value.FIVE,'5');
        m.put(Value.SIX,'6');
        m.put(Value.SEVEN,'7');
        m.put(Value.EIGHT,'8');
        m.put(Value.NINE,'9');
        m.put(Value.TEN,'T');
        m.put(Value.JACK,'J');
        m.put(Value.QUEEN,'Q');
        m.put(Value.KING,'K');

        valuePrintMap = Collections.unmodifiableMap(m);
    }

    private static final Map<Suit,Character> suitPrintMap;
    static {
        Map<Suit,Character> m = new HashMap<Suit,Character>();
        m.put(Suit.SPADE,'\u2660');
        m.put(Suit.HEART,'\u2665');
        m.put(Suit.DIAMOND,'\u2666');
        m.put(Suit.CLUB,'\u2663');
        //m.put(Suit.SPADE,'S');
        //m.put(Suit.HEART,'H');
        //m.put(Suit.DIAMOND,'D');
        //m.put(Suit.CLUB,'C');

        suitPrintMap = Collections.unmodifiableMap(m);
    }

}
