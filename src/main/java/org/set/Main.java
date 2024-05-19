package org.set;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        JFrame frame = new JFrame("Hexagon Game Board");
//        HexagonGameBoard board = new HexagonGameBoard(15, 35, 40);
//        frame.add(board);
//        frame.pack();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//        GameController GameControl = new GameController(board);

        Card[] cards = {
            new ExpeditionCard("Explorer", 0, false, 1, CardType.GREEN),
            new ExpeditionCard("Scout", 1, false, 2, CardType.GREEN),
            new ExpeditionCard("Trail????", 3, false, 3, CardType.GREEN),
            new ExpeditionCard("Pioneer", 5, false, 5, CardType.GREEN),
            new ExpeditionCard("Giant Machete", 3, true, 6, CardType.GREEN),
            new ExpeditionCard("Sailor", 0, false, 1, CardType.BLUE),
            new ExpeditionCard("Captain", 2, false, 3, CardType.GREEN),
            new ExpeditionCard("Traveller", 0, false, 1, CardType.YELLOW),
            new ExpeditionCard("Photographer", 2, false, 2, CardType.YELLOW),
            new ExpeditionCard("Journalist", 3, false, 3, CardType.YELLOW),
            new ExpeditionCard("Treasure chest", 3, true, 4, CardType.YELLOW),
            new ExpeditionCard("Millionaire", 4, false, 4, CardType.YELLOW),
            new ExpeditionCard("Jack????", 2, false, 1, CardType.JOKER),
            new ExpeditionCard("Adventure????", 4, false, 2, CardType.JOKER),
            new ExpeditionCard("Prop Plane", 4, true, 4, CardType.JOKER),
            new ActionCard("Transmitter", 4, true),
            new ActionCard("Cartographer", 4, false),
            new ActionCard("Scientist", 4, false),
            new ActionCard("Compass", 2, true),
            new ActionCard("Travel Log", 3, true),
            new ActionCard("Native", 5, false),
        };

        Player player = new Player(1, Color.BLACK);

        ActionCard actionCard = (ActionCard) cards[18];
        System.out.println(actionCard.isPlayable());
        actionCard.doAction(actionCard, player);
        System.out.println(actionCard.isPlayable());
    }
}