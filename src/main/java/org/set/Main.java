package org.set;

import javax.swing.*;

import org.set.boardPieces.HexagonGameBoard;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hexagon Game Board");
        HexagonGameBoard board = new HexagonGameBoard(35, 35, 25);
        frame.add(board);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        GameController GameControl = new GameController(board);
    }
}