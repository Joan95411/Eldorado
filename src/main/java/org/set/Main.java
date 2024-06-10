package org.set;


import javax.swing.*;

import org.set.boardPieces.HexagonGameBoard;
import org.set.game.GameController;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hexagon Game Board");
        HexagonGameBoard board = new HexagonGameBoard(35, 35, 25,false);
        JScrollPane scrollPane = new JScrollPane(board);
        frame.add(scrollPane);
//        frame.add(board);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        GameController GameControl = new GameController(board);
    	 

    }
     }
    
