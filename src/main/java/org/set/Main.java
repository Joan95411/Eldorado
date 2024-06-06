package org.set;

import javax.swing.*;

import org.set.boardPieces.HexagonGameBoard;
import org.set.game.GameController;

public class Main {
    public static void main(String[] args) {
         JFrame frame = new JFrame("Hexagon Game Board");
         HexagonGameBoard board = new HexagonGameBoard(15, 35, 30);
         frame.add(board);
         frame.pack();
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setVisible(true);
         new GameController(board);
    }
}