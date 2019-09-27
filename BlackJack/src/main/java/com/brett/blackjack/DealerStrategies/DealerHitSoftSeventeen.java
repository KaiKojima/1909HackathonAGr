package com.brett.blackjack.DealerStrategies;

import com.brett.blackjack.BlackJackGame;

public class DealerHitSoftSeventeen extends AbstractDealerHandResolver{

    public DealerHitSoftSeventeen(BlackJackGame blackJackGame) {
        super(blackJackGame);
    }

    public DealerHitSoftSeventeen() {
    }

    @Override
    public void resolveDealerHand() {
        while (dealerBelowSeventeen()) {
            blackJackGame.dealerHit();
        }
    }

    //Dealer should hit soft seventeen because one total is < 17
    boolean dealerBelowSeventeen() {
        boolean belowFound = false;
        for (Integer totals : blackJackGame.getDealersHand().getTotals()) {
            if (totals < 17) {
                return true;
            }
        }
        return belowFound;
    }
}