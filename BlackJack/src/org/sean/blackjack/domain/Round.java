package org.sean.blackjack.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * The round object represents the current state of the BlackJack game. It is
 * the object that is passed to the client as a JSON object.
 * 
 */
public final class Round {
	private static Round instance = new Round();
	private final List<Card> dealerCards = new ArrayList<Card>();
	private final List<Card> playerCards = new ArrayList<Card>();
	// playerHandValue is a String because Aces have a value of "1 or 11"
	// and sometimes this fact needs to be displayed to the client.
	private String playerHandValue = Consts.BLANK_MESSAGE;
	private String gameMessage = Consts.BLANK_MESSAGE;
	private double playerCredits;
	private int playerBet;
	private int dealerHandValue;
	private boolean bustPlayer;
	private boolean playerHasBlackJack;
	private boolean playerCanSplit;
	// SplitHand is lazily initialized when the player splits the cards. This 
	// reduces the size of the JSON object during normal "non-split hand" play.
	private SplitHand splitHand = null;

	
	/**
	 * The Constructor is private because Round is a Singleton
	 */
	private Round() {
		super();
	}
	
	
	/**
	 * Round is a Singleton and can only be accessed by other methods using
	 * this method.
	 * 
	 * @return - the singleton instance of Round
	 */
	public static Round getInstance(){
		return instance;
	}

	/**
	 * Calculate the values of both the player's and dealer's hands for display
	 * to the client. Convert the player's hand value to a string. In the case that the
	 * player has different possible hand values due to an ace, modify the
	 * string to show these two possible values.
	 * 
	 * @param thePlayerIsFinishedDrawingCards
	 *            - boolean indicating that the player is finished taking cards.
	 */
	public void calculateHandValues(boolean thePlayerIsFinishedDrawingCards) {
		// The boolean thePlayerIsFinishedDrawingCards indicates that the player has
		// received all  their cards. In this case, the Ace will be set to a fixed
		// value of either 1 or 11.
		int total = 0;
		int numberOfDealersAces = 0;
		int numberOfPlayersAces = 0;

		for (Card card : this.playerCards) {
			total = total + card.getRank().getCardValue();
			numberOfPlayersAces = ifAceThenIncrementAceCount(
					numberOfPlayersAces, card);
		}
		this.playerHandValue = String.valueOf(total);
		// Check if the player's hand value is 11 or less and the player has at least one
		// ace. If so, add 10 to the player's hand value if the player is finished drawing
		// cards, and if the player is still drawing cards show the two possible hand values
		// for a hand with an Ace.
		if ((total <= 11) && numberOfPlayersAces != 0) {
			if (thePlayerIsFinishedDrawingCards) {
				this.playerHandValue = String.valueOf(total + 10);
			} else {
				this.playerHandValue = this.playerHandValue + " or "
						+ String.valueOf(total + 10);
			}
		}
		total = 0;
		for (Card card : this.dealerCards) {
			total = total + card.getRank().getCardValue();
			numberOfDealersAces = ifAceThenIncrementAceCount(
					numberOfDealersAces, card);
		}
		if ((total <= 11) && numberOfDealersAces != 0) {
			this.dealerHandValue = total + 10;
		} else {
			this.dealerHandValue = total;
		}
	}

	/**
	 * If the card passed to the method is an ace, increment and return the
	 * numberOfAces count.
	 * 
	 * @param numberOfAces
	 *            - the number of aces
	 * @param card
	 *            - the card to be checked if an ace or not
	 * @return numberOfAces - the number of aces
	 */
	private int ifAceThenIncrementAceCount(int numberOfAces, Card card) {
		if ((card.getRank().getCardValue() == 1)) {
			numberOfAces++;
		}
		return numberOfAces;
	}

	/**
	 * Check that the value of the hand has not gone above 21. For the purposes
	 * of this check, an ace will always have a value of 1. If the player is
	 * bust, deduct the player's bet from the player's credits. If the dealer is
	 * bust, add an amount matching the player's bet to the player's credits.
	 * 
	 * @param cards
	 *            - the list of cards to be checked
	 * @param isPlayer
	 *            - true if this is the player's hand
	 * @return boolean - true if the cards are over 21
	 */
	public boolean checkBust(List<Card> cards, boolean isPlayer, boolean isSplit) {	
		if (cards == null) {
			throw new IllegalArgumentException("Invalid Hand of Cards " + cards);//fail fast			
		}
		
		int maxHandValueAllowed = Consts.TWENTY_ONE;
		int total = 0;
		
		for (Card card : cards) {
			total = total + card.getRank().getCardValue();
		}
		if (isPlayer) {
			if (total > maxHandValueAllowed) {
				this.bustPlayer = true;
				this.gameMessage = Consts.PLAYER_BUST;
				this.playerCredits -= this.playerBet;
				checkIfPlayerLowOnCredits();
				return true;
			}
		} else {
			// Check for a bust dealer.
			if (total > maxHandValueAllowed) {
				this.gameMessage = Consts.DEALER_BUST;
				if (!isSplit) {
					// Not a split hand
					this.playerCredits += this.playerBet;
				} 
				// The following is a special case. During a split hand, if the
				// dealer goes bust, the player will have either one or two hands
				// that haven't gone bust. So the player will have one or two winning
				// hands. This else statement determines if the player wins once or 
				// twice and sets the left and right player game messages accordingly.
				else {				
					if (!this.splitHand.isSplitLeftBust()) {
						this.splitHand
								.setSplitLeftGameMessage(Consts.DEALER_BUST);
					}
					if (!this.splitHand.isSplitRightBust()) {
						this.splitHand
								.setSplitRightGameMessage(Consts.DEALER_BUST);
					}
					// The dealer is bust. If neither of the player's hands are
					// bust, then increment
					// the player credits by both bets
					if (!this.splitHand.isSplitLeftBust()
							&& !this.splitHand.isSplitRightBust()) {
						this.playerCredits += 2 * this.playerBet;
					} else {
						// One of the split hands is bust. This code is not
						// reached if both hands are bust
						this.playerCredits += this.playerBet;
					}
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if the player has a blackjack, i.e. an Ace and a card with a value
	 * of 10.
	 * 
	 * @return boolean - true if the player has a BlackJack
	 */
	public boolean hasPlayerABlackJack() {
		int totalPlayer = 0;
		int numberOfPlayersAces = 0;
		
		for (Card card : this.playerCards) {
			totalPlayer = totalPlayer + card.getRank().getCardValue();
			numberOfPlayersAces = ifAceThenIncrementAceCount(
					numberOfPlayersAces, card);
		}
		// An ace has a value of 1 in the enum "Rank". So a Blackjack will add
		// up to a value of 11
		if ((totalPlayer == 11) && (this.playerCards.size() == 2)
				&& (numberOfPlayersAces == 1)) {
			this.playerHasBlackJack = true;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check the dealer's initial visible card to see if it is possible for the
	 * dealer to make a BlackJack or not. If the initial visible card has a
	 * value between 2 and 9 inclusive, then it is impossible for the dealer to
	 * make a BlackJack. If it is an Ace, a ten or a royal, then it is possible.
	 * 
	 * @return boolean - true if the dealer can not make a BlackJack based on an 
	 * examination of the dealer's starting public card.
	 */
	public boolean dealerCanNotMakeBlackJack() {
		int cardValueOfAce = Rank.ACE.getCardValue();
		int cardValueOfTenOrRoyal = Rank.TEN.getCardValue();
		
		if ((this.dealerCards.get(0).getRank().getCardValue() != cardValueOfAce)
				&& (this.dealerCards.get(0).getRank().getCardValue() != cardValueOfTenOrRoyal)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check to see if the total value of the dealer's hand has a value of
	 * between 17 and 21 inclusive.
	 * 
	 * @return boolean - true if the dealer's hand value is between 17 and 21
	 *  inclusive
	 */
	public boolean dealerMustStand() {
		int total = 0;
		int numberOfDealersAces = 0;
		
		for (Card card : this.dealerCards) {
			total = total + card.getRank().getCardValue();
			numberOfDealersAces = ifAceThenIncrementAceCount(
					numberOfDealersAces, card);
		}
		// A dealer's ace must have a value of 11 as long as that does not
		// cause the dealer to go bust. Otherwise it keeps the default value of
		// 1.
		for (int i = 0; i < numberOfDealersAces; i++) {
			if (total <= 11) {
				total = total + 10;
			}
		}
		if ((total >= Consts.MIN_VALUE_THAT_DEALER_STANDS) && (total <= Consts.TWENTY_ONE)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check to see whether the player or the dealer has the best
	 * hand. If the dealer wins, subtract the player's bet from the player
	 * credits. If both player and dealer are equal strength (called a push)
	 * then do not change the player credits. If the player wins with a
	 * BlackJack (10 or royal and Ace), then increase the player's credits by 
	 * 1.5 times the bet. If the player wins without a BlackJack, then increase
	 * the player's credits by the size of the bet.
	 */
	public void checkWhoWon() {
		int totalPlayer = 0;
		int totalDealer = 0;
		int numberOfPlayersAces = 0;
		int numberOfDealersAces = 0;

		for (Card card : this.playerCards) {
			totalPlayer = totalPlayer + card.getRank().getCardValue();
			numberOfPlayersAces = ifAceThenIncrementAceCount(
					numberOfPlayersAces, card);
		}
		totalPlayer = adjustHandValueForAces(totalPlayer, numberOfPlayersAces);
		// Check to see if player has a BlackJack (an Ace and a card of value
		// 10).
		// This is possible here because the player's hand value has been
		// adjusted for aces
		if ((totalPlayer == Consts.TWENTY_ONE)
				&& (this.playerCards.size() == 2)) {
			this.playerHasBlackJack = true;
		}
		for (Card card : this.dealerCards) {
			totalDealer = totalDealer + card.getRank().getCardValue();
			numberOfDealersAces = ifAceThenIncrementAceCount(
					numberOfDealersAces, card);
		}
		totalDealer = adjustHandValueForAces(totalDealer, numberOfDealersAces);

		if (totalPlayer == totalDealer) {
			// Check to see if the player has BlackJack (an Ace and a card of
			// value
			// 10) but the dealer doesn't. If so, the player wins.
			if (this.playerHasBlackJack && (this.dealerCards.size() > 2)) {
				this.gameMessage = Consts.PLAYER_WINS_WITH_BLACKJACK;
				this.playerCredits += 1.5 * this.playerBet;
				// Check to see if the dealer has BlackJack (an Ace and a card
				// of value 10) but the player doesn't. If so, the dealer wins.
			} else if ((totalDealer == Consts.TWENTY_ONE)
					&& (this.dealerCards.size() == 2)
					&& (!this.playerHasBlackJack)) {
				this.gameMessage = Consts.DEALER_WINS_WITH_BLACKJACK;
				this.playerCredits -= this.playerBet;
				// If the player is now low on credits, set playerLowOnCredits
				// to true.
				checkIfPlayerLowOnCredits();
			} else {
				this.gameMessage = Consts.DRAW;
			}
		}

		if (totalPlayer > totalDealer) {
			if (this.playerHasBlackJack) {
				this.gameMessage = Consts.PLAYER_WINS_WITH_BLACKJACK;
				this.playerCredits += 1.5 * this.playerBet;
			} else {
				this.gameMessage = Consts.PLAYER_WINS;
				this.playerCredits += this.playerBet;
			}
		}

		if (totalPlayer < totalDealer) {
			if ((totalDealer == Consts.TWENTY_ONE)
					&& (this.dealerCards.size() == 2)) {
				this.gameMessage = Consts.DEALER_WINS_WITH_BLACKJACK;
			} else {
				this.gameMessage = Consts.PLAYER_LOSES;
			}
			this.playerCredits -= this.playerBet;
			// If the player is now low on credits, set playerLowOnCredits
			// to true.
			checkIfPlayerLowOnCredits();
		}
	}

	/**
	 * Check whether the player is low on credits. If so, set playerLowOnCredits
	 * to "true"
	 */
	public void checkIfPlayerLowOnCredits() {
		if (this.playerCredits < Consts.LOW_CREDITS_VALUE) {
			this.playerCredits = Consts.STARTING_CREDITS;
			this.gameMessage = Consts.LOW_CREDITS_MESSAGE;
		}
	}

	/**
	 * Check if the player's initial two cards have the same card value. If so,
	 * then set a boolean flag to true. Note that 10, Jack, Queen and King all
	 * have the same card value in BlackJack.
	 */
	public void checkIfPlayerCanSplit() {
		if (this.playerCards.get(0).getRank().getCardValue() == this.playerCards
				.get(1).getRank().getCardValue()) {
			this.setPlayerCanSplit(true);
		}
	}

	/**
	 * If the player splits the cards, make two new empty hands. Move the
	 * player's first card into one of the new hands. Move the player's second
	 * card into the other new hand.
	 */
	public void playerSplits() {
		this.splitHand = new SplitHand();
		this.splitHand.getSplitLeftCards().add(this.playerCards.get(0));
		this.splitHand.getSplitRightCards().add(this.playerCards.get(1));
		this.gameMessage = Consts.PLAYER_SPLITS;
	}

	/**
	 * For each of the player's hands that isn't already bust , check to see who
	 * won between the player and the dealer. Blackjacks are treated as normal
	 * hands of 21 for split hands. If the dealer wins, subtract the player's
	 * bet from the player credits. If both player and dealer are equal strength
	 * (called a push) then do not change the player credits. If the player wins
	 * then increase the player's credits by the size of the bet.
	 * 
	 * Note that the player's bet applies to each hand so it is possible to win
	 * or lose twice the bet if the player has two hands still in play (not
	 * bust).
	 */
	public void checkWhoWonAfterSplit() {
		int totalPlayer = 0;
		int totalDealer = 0;
		int numberOfPlayersAces = 0;
		int numberOfDealersAces = 0;

		// Compare left hand side first
		if (!this.splitHand.isSplitLeftBust()) {
			for (Card card : this.splitHand.getSplitLeftCards()) {
				totalPlayer = totalPlayer + card.getRank().getCardValue();
				numberOfPlayersAces = ifAceThenIncrementAceCount(
						numberOfPlayersAces, card);
			}
			totalPlayer = adjustHandValueForAces(totalPlayer,
					numberOfPlayersAces);
			for (Card card : this.dealerCards) {
				totalDealer = totalDealer + card.getRank().getCardValue();
				numberOfDealersAces = ifAceThenIncrementAceCount(
						numberOfDealersAces, card);
			}
			totalDealer = adjustHandValueForAces(totalDealer,
					numberOfDealersAces);
			if (totalPlayer == totalDealer) {
				this.splitHand.setSplitLeftGameMessage(Consts.DRAW);
			}
			if (totalPlayer > totalDealer) {
				this.splitHand.setSplitLeftGameMessage(Consts.PLAYER_WINS);
				this.playerCredits += this.playerBet;
			}
			if (totalPlayer < totalDealer) {
				this.splitHand.setSplitLeftGameMessage(Consts.PLAYER_LOSES);
				this.playerCredits -= this.playerBet;
			}
		}

		// Compare right hand side
		if (!this.splitHand.isSplitRightBust()) {
			totalPlayer = 0;
			totalDealer = 0;
			numberOfPlayersAces = 0;
			numberOfDealersAces = 0;

			for (Card card : this.splitHand.getSplitRightCards()) {
				totalPlayer = totalPlayer + card.getRank().getCardValue();
				numberOfPlayersAces = ifAceThenIncrementAceCount(
						numberOfPlayersAces, card);
			}
			// Adjust player's hand value for any aces
			totalPlayer = adjustHandValueForAces(totalPlayer,
					numberOfPlayersAces);
			for (Card card : this.dealerCards) {
				totalDealer = totalDealer + card.getRank().getCardValue();
				numberOfDealersAces = ifAceThenIncrementAceCount(
						numberOfDealersAces, card);
			}
			totalDealer = adjustHandValueForAces(totalDealer,
					numberOfDealersAces);			
			if (totalPlayer == totalDealer) {
				this.splitHand.setSplitRightGameMessage(Consts.DRAW);
			}		
			if (totalPlayer > totalDealer) {
				this.splitHand.setSplitRightGameMessage(Consts.PLAYER_WINS);
				this.playerCredits += this.playerBet;
			}		
			if (totalPlayer < totalDealer) {
				this.splitHand.setSplitRightGameMessage(Consts.PLAYER_LOSES);
				this.playerCredits -= this.playerBet;
				// If the player is now low on credits, set playerLowOnCredits
				// to true.
				this.checkIfPlayerLowOnCredits();
			}
		}
	}
	
	/**
	 * @param total - the unmodified total hand value.
	 * @param numberOfAces - the number of aces in the hand
	 * @return total - the modified total hand value.
	 */
	private int adjustHandValueForAces(int total, int numberOfAces) {
			if ( (total <= 11) && (numberOfAces != 0) ) {
				total = total + 10;
			}
		return total;
	}

	public boolean isBustPlayer() {
		return bustPlayer;
	}

	public void setBustPlayer(boolean bustPlayer) {
		this.bustPlayer = bustPlayer;
	}

	public double getPlayerCredits() {
		return playerCredits;
	}

	public void setPlayerCredits(double playerCredits) {
		if (playerCredits < 0) {
			throw new IllegalArgumentException("Invalid Amount " + playerCredits);//fail fast			
		}
		
		this.playerCredits = playerCredits;
	}

	public int getPlayerBet() {
		return playerBet;
	}

	public void setPlayerBet(int playerBet) {
		if (playerBet <= 0) {
			throw new IllegalArgumentException("Invalid Amount " + playerBet);//fail fast			
		}
		this.playerBet = playerBet;
	}

	public List<Card> getDealerCards() {
		return dealerCards;
	}

	public List<Card> getPlayerCards() {
		return playerCards;
	}

	public String getGameMessage() {
		return gameMessage;
	}

	public void setGameMessage(String gameMessage) {
		if (gameMessage == null) {
			throw new IllegalArgumentException("Invalid String " + gameMessage);//fail fast			
		}
		this.gameMessage = gameMessage;
	}

	public boolean isPlayerHasBlackJack() {
		return playerHasBlackJack;
	}

	public void setPlayerHasBlackJack(boolean playerHasBlackJack) {
		this.playerHasBlackJack = playerHasBlackJack;
	}

	//Not referred to by any code except JUnits. Candidate for removal.
	public String getPlayerHandValue() {
		return playerHandValue;
	}
	//Not referred to by any code except JUnits. Candidate for removal.
	public void setPlayerHandValue(String playerHandValue) {
		this.playerHandValue = playerHandValue;
	}
	//Not referred to by any code except JUnits. Candidate for removal.
	public int getDealerHandValue() {
		return dealerHandValue;
	}
	//Not referred to by any code except JUnits. Candidate for removal.
	public void setDealerHandValue(int dealerHandValue) {
		this.dealerHandValue = dealerHandValue;
	}
	//Not referred to by any code except JUnits. Candidate for removal.
	public boolean isPlayerCanSplit() {
		return playerCanSplit;
	}

	public void setPlayerCanSplit(boolean playerCanSplit) {
		this.playerCanSplit = playerCanSplit;
	}

	public SplitHand getSplitHand() {
		return splitHand;
	}

	public void setSplitHand(SplitHand splitHand) {
		// Do not fail fast test for splitHand equal to null here as setting splitHand to null
		// is valid. splitHand is set to null at the start of each round to minimize the size 
		// of the JSON object returned to the client.
		this.splitHand = splitHand;
	}

}
