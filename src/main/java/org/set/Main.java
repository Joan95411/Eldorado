package org.set;


import javax.swing.*;

import org.set.boardPieces.HexagonGameBoard;
import org.set.game.GameController;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hexagon Game Board");
        HexagonGameBoard board = new HexagonGameBoard(25, 35, 25,false);
        JScrollPane scrollPane = new JScrollPane(board);
        frame.add(scrollPane);
        frame.setSize(1250, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        GameController GameControl = new GameController(board);
    	 

    }
     }
    
