package com.brett.blackjack;

import java.util.ArrayList;
import java.util.List;

public enum Value {
    ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO;

    private Integer intVal;

    static  {
        KING.intVal = 10;
        QUEEN.intVal = 10;
        JACK.intVal = 10;
        TEN.intVal = 10;
        NINE.intVal = 9;
        EIGHT.intVal = 8;
        SEVEN.intVal = 7;
        SIX.intVal = 6;
        FIVE.intVal = 5;
        FOUR.intVal = 4;
        THREE.intVal = 3;
        TWO.intVal = 2;
    }

    public Integer value(){
        return intVal;
    }
}
