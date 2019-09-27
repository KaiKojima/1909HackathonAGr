package com.brett.blackjack;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("game")
@RequestMapping("/")
public class GameController {
	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("message", "Welcome to Blackjack! &nbsp Click to start a new game");
		return "mainMenu";
	}

    @RequestMapping(method = RequestMethod.POST)
    public String startNewGame(HttpSession session){
        session.setAttribute("game", new BlackJackGame());
        return "inProgress";
    }

    @RequestMapping(method = RequestMethod.POST, params = "hit")
    public String hit(HttpSession session) {
        BlackJackGame game = (BlackJackGame) session.getAttribute("game");
        game.playerHit();
        if(game.playerBusted()){
            game.resolveDealerHand();
            return "endGame";
        }else{
            return "inProgress";
        }
    }

    @RequestMapping(method = RequestMethod.POST, params = "stand")
    public String stand(HttpSession session) {
        BlackJackGame game = (BlackJackGame) session.getAttribute("game");
        game.resolveDealerHand();
        return "endGame";
    }


}