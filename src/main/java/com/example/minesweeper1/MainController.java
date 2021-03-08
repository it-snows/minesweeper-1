package com.example.minesweeper1;

import com.example.minesweeper1.data.Board;
import com.example.minesweeper1.data.Game;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @GetMapping("")
    public String getIndexPage(Model model) {

        if(Minesweeper1Application.game == null){
            Minesweeper1Application.game = new Game (6);
        }

        model.addAttribute("game", getGameHtml(Minesweeper1Application.game));

        return "index";
    }

    @GetMapping("/check")
    public String checkField(Model model, @RequestParam int row, @RequestParam int column) {

        if(Minesweeper1Application.game == null){
            Minesweeper1Application.game = new Game (6);
        }

        Minesweeper1Application.game.checkField(row, column);

        model.addAttribute("game", getGameHtml(Minesweeper1Application.game));

        return "index";
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
                return "<a class=\"field-closed\" href=\"/check?row=" + row + "&column=" + column + "\">&nbsp;</a>";
            }
        }
        return "<div class=\"field-number\"><span class=\"number\">" + i + "</span></div>";
    }
}
