package com.brett.blackjack.DealerStrategies;

import com.brett.blackjack.BlackJackGame;

public abstract class AbstractDealerHandResolver implements DealerHandResolver {

    protected BlackJackGame blackJackGame;

    public AbstractDealerHandResolver(BlackJackGame blackJackGame) {
        this.blackJackGame = blackJackGame;
    }

    protected AbstractDealerHandResolver() {
    }
}
