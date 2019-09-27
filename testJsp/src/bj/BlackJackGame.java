package bj;



import org.springframework.beans.factory.annotation.Autowired;

import com.brett.blackjack.DealerStrategies.DealerHandResolver;

public class BlackJackGame {
	private Shoe shoe;
	private Hand dealersHand;
	private Hand playersHand;

	//todo: fix this so dealer's strategy can be injected
	@Autowired
	private DealerHandResolver dealerHandResolver;

	public BlackJackGame() {
		this.shoe = new Shoe(6);

		dealersHand = new Hand();
		dealersHand.addCard(shoe.draw());

		playersHand = new Hand();
		playersHand.addCard(shoe.draw());
		playersHand.addCard(shoe.draw());

	}

	public Hand getDealersHand() {
		return dealersHand;
	}

	public Hand getPlayersHand() {
		return playersHand;
	}

	public void setPlayersHand(Hand hand) {
		this.playersHand = hand;
	}

	public void setDealersHand(Hand hand) {
		this.dealersHand = hand;
	}

	public void playerHit() {
		playersHand.addCard(shoe.draw());
	}

	public void dealerHit() {
		dealersHand.addCard(shoe.draw());
	}

	public boolean playerBusted() {
		boolean allTotalsBusted = true;
		for (Integer totals : playersHand.getTotals()) {
			if (totals <= 21) {
				allTotalsBusted = false;
			}
		}
		return allTotalsBusted;
	}

	//Return false if any total is less than 21
	public boolean dealerBusted() {
		for (Integer totals : dealersHand.getTotals()) {
			if (totals <= 21) {
				return false;
			}
		}
		return true;
	}

	public void resolveDealerHand() {
		while (!dealerBusted() && dealerBelowSeventeen()) {
			dealerHit();
		}
	}

	//If any total is >= 17 but not a bust do no hit
	private boolean dealerBelowSeventeen() {
		for (Integer totals : getDealersHand().getTotals()) {
			if (totals >= 17 && totals < 22) { //Do not hit if dealers has 17 or greater
				return false;
			}
		}
		return true;
	}

	public String result() {
		if (playerBusted() || dealersHand.blackJack()) {
			return "You lose! :(";
		} else if (playersHand.blackJack()) {
			return "You win!";
		} else if (playersHand.finalTotal().equals(dealersHand.finalTotal())) {
			return "Push";
		} else if ((playersHand.finalTotal() < dealersHand.finalTotal()) && (dealersHand.finalTotal() < 22)) {
			return "You lose! :(";
		} else {
			return "You win!";
		}
	}

}
