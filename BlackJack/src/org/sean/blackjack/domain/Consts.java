package org.sean.blackjack.domain;

/**
 * A collection of constants of general utility
 *
 */
public final class Consts  {

	  public static final int STARTING_CREDITS = 1000;
	  public static final int STARTING_BET = 10;
	  public static final int LOW_CREDITS_VALUE = 320;
	  // The maximum value of a valid hand in BlackJack is 21. 
	  // Any more than this and the hand is "bust".
	  public static final int  TWENTY_ONE = 21;
	  public static final int  MIN_VALUE_THAT_DEALER_STANDS = 17;
	  // The following strings are in-game messages to be displayed 
	  // to the client during a game of BlackJack.
	  public static final String BLANK_MESSAGE = "";
	  public static final String PLAYER_WINS = "You won!";
	  public static final String PLAYER_WINS_WITH_BLACKJACK = "BlackJack!!! You won one and a half times your bet.";
	  public static final String DEALER_WINS_WITH_BLACKJACK = "The Dealer won with a BlackJack. The Dealer has taken your bet.";
	  public static final String PLAYER_LOSES = "You lost! The Dealer has taken your bet.";
	  public static final String DRAW = "The game was a draw.";
	  public static final String PLAYER_BUST = "You lost because you went bust (over 21). The Dealer has taken your bet.";
	  public static final String DEALER_BUST = "The Dealer went bust! You won.";
	  public static final String LOW_CREDITS_MESSAGE = "You lost! Because your credits were low, they have been topped up.";
	  public static final String PLAYER_SPLITS = "When you split, your bet applies to each hand and all BlackJacks are simply treated as 21.";

	  /**
	 * The constructor is private as it is not supposed
	 * to be instantiated.
	 */
	private Consts(){
		
	    //This prevents even the native class from 
	    //calling this constructor:
	    throw new AssertionError();
	  }
	}
