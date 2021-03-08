package com.example.minesweeper1;

import com.example.minesweeper1.data.Board;
import com.example.minesweeper1.data.Game;
import com.example.minesweeper1.data.GameResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    @GetMapping("")
    public String getIndexPage(Model model, @RequestParam(required = false) Integer fieldSize) {

        var gameStr = "";

        if (fieldSize != null) {
            if (Minesweeper1Application.game == null) {
                Minesweeper1Application.game = new Game(fieldSize);
            }
            gameStr = getGameHtml(Minesweeper1Application.game);
        }

        model.addAttribute("game", gameStr);
        model.addAttribute("status", GameResult.CONTINUE.toString());

        return "index";
    }

    @GetMapping("/check")
    public String checkField(Model model, @RequestParam int row, @RequestParam int column) {

        if (Minesweeper1Application.game == null) {
            Minesweeper1Application.game = new Game(6);
        }

        var result = Minesweeper1Application.game.checkField(row, column);

        model.addAttribute("status", result.toString());
        model.addAttribute("game", getGameHtml(Minesweeper1Application.game));


        return "index";
    }

    @GetMapping("/newgame")
    public ModelAndView newGame() {

        Minesweeper1Application.game = null;

        return new ModelAndView("redirect:/");
    }

    private String getGameHtml(Game game) {
        var html = new StringBuilder();

        for (int i = 0; i < game.getSize(); i++) {
            html.append("<div class=\"row\">");
            for (int j = 0; j < game.getSize(); j++) {
                html.append("<div class=\"field\">" + getFieldIcon(game.getFieldValue(i, j), i, j) + "</div>");
            }
            html.append("</div>");
        }

        return html.toString();
    }

    private String getFieldIcon(int i, int row, int column) {

        switch (i) {
            case Board.MINE_VALUE: {
                return "<div class=\"field-bomb\"><img src=\"/img/bomb.svg\" class=\"field-icon\"></div>";
            }
            case Board.FIELD_FLAGGED: {
                return "<img src=\"/img/flag.svg\" class=\"field-icon\">";
            }
            case Board.FIELD_IS_EMPTY: {
                return "<div class=\"field-empty\">&nbsp;</div>";
            }
            case Board.FIELD_CLOSED: {

                if (Minesweeper1Application.game.isGameFinished()) {

                    return "<div class=\"field-closed\"></div>";

                } else {

                    return "<a class=\"field-closed\" href=\"/check?row=" + row + "&column=" + column + "\">&nbsp;</a>";
                }
            }
        }
        return "<div class=\"field-number\"><span class=\"number\">" + i + "</span></div>";
    }
}
