package com.example.minesweeper1;

import com.example.minesweeper1.data.Game;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Minesweeper1Application {

    public static Game game;

    public static void main(String[] args) {
        SpringApplication.run(Minesweeper1Application.class, args);
    }

}
