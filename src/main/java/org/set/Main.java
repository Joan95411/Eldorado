package org.set;


import javax.swing.*;

import org.set.boardPieces.HexagonGameBoard;
import org.set.gameController.GameController;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hexagon Game Board");
        HexagonGameBoard board = new HexagonGameBoard(35, 45, 25,true);
        JScrollPane scrollPane = new JScrollPane(board);
        frame.add(scrollPane);
//        frame.add(board);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        GameController GameControl = new GameController(board);
    	 

    }
     }
    
