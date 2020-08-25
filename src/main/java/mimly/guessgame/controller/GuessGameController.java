package mimly.guessgame.controller;

import lombok.extern.slf4j.Slf4j;
import mimly.guessgame.model.Guess;
import mimly.guessgame.model.GuessGame;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j(topic = "** Guess Game **")
@WebServlet(urlPatterns = {"/", "/gameover", "/invalidate"}, loadOnStartup = 1)
public class GuessGameController extends HttpServlet {

    private void doIndexGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        GuessGame guessGame = (GuessGame) httpSession.getAttribute("guessGame");

        if (guessGame.isOver()) {
            resp.sendRedirect("/gameover");
            return;
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/views/index.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void doGameOverGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        GuessGame guessGame = (GuessGame) httpSession.getAttribute("guessGame");

        if (!guessGame.isOver()) {
            resp.sendRedirect("/");
            return;
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/views/gameover.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void doInvalidateGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession httpSession = req.getSession();
        httpSession.invalidate();
        resp.sendRedirect("/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String path = req.getServletPath();
        if (path.equalsIgnoreCase("/")) {
            doIndexGet(req, resp);
        } else if (path.equalsIgnoreCase("/gameover")) {
            doGameOverGet(req, resp);
        } else if (path.equalsIgnoreCase("/invalidate")) {
            doInvalidateGet(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Guess guess = new Guess(req.getParameter("param"));
        log.info("Received: " + guess);

        HttpSession httpSession = req.getSession();
        synchronized (httpSession) {
            GuessGame guessGame = (GuessGame) httpSession.getAttribute("guessGame");

            guessGame.updateErrorMessage(guess);
            if (guessGame.isValid(guess)) {
                guessGame.setLimits(guess);
                if (guessGame.isCorrect(guess)) {
                    guessGame.setOver(true);
                    resp.sendRedirect("/gameover");
                    return;
                }
            }
        }

        resp.sendRedirect("/");
    }
}
