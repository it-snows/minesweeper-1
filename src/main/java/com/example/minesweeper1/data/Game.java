package com.example.minesweeper1.data;

import lombok.Getter;

public class Game {
    private Board board;

    @Getter
    private final int size;

    public Game(int size) {
        board = new Board(size);
        this.size = size;
        start();
    }

    public void start() {
        board.initBoard();
    }

    public GameResult checkField(int row, int column) {
        var result = board.checkField(row, column);

        if(!result) {
            return GameResult.LOST;
        }

        if(board.isOpen()) {
            return GameResult.WON;
        }

        return GameResult.CONTINUE;
    }

    public int getFieldValue(int row, int column) {
        return board.getFieldInfo(row, column);
    }
}