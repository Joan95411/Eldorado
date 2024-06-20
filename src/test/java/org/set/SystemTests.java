package org.set;

import java.io.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import javax.swing.*;
import org.junit.jupiter.api.BeforeEach;
import org.set.game.EldoradoGame;
import org.set.game.GameController;
import org.set.game.InputHelper;
import org.set.template.Team03Board;
import org.set.template.Team04Board;
import org.set.template.Template;

import static org.junit.jupiter.api.Assertions.*;

public class SystemTests {
    public EldoradoGame eldoradoGame;

    @Test
    public void startMain() throws InterruptedException {
        eldoradoGame = new EldoradoGame();
        Thread gameThread = new Thread(eldoradoGame);
        gameThread.start();
        doUserInput("n\n2\nblue\nred\n");
        Thread.sleep(1000);

        //System.out.println("n");
        Thread.sleep(4000);
        //Thread.sleep(1000);
    }

    public static void doUserInput(String input){
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        InputHelper.setInputStream(inputStream);
    }

    @Test
    public void SystemTest1() throws InterruptedException {
        eldoradoGame = new EldoradoGame();
        Thread gameThread = new Thread(eldoradoGame);
        gameThread.start();
        doUserInput("n\n2\nblue\nred\n");
        Thread.sleep(1000);

        //System.out.println("n");
        Thread.sleep(4000);
        //Thread.sleep(1000);
        //Thread.sleep(1000);
        //do input

        //test some condition to be true
        //System.out.println(eldoradoGame.gameControl);
        assertNotNull(eldoradoGame.board);
        assertNotNull(eldoradoGame.gameControl);
    }
}
