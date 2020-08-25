package mimly.guessgame.controller;

import mimly.guessgame.model.GuessGame;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class GuessGameHttpSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession httpSession = se.getSession();
        GuessGame guessGame = new GuessGame(0, 100, 89);
        httpSession.setAttribute("guessGame", guessGame);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }
}
