package org.set;

public class Main {
    public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
        JFrame frame = new JFrame("Hexagon Game Board");
        HexagonGameBoard board = new HexagonGameBoard(11, 11, 50);
        frame.add(board);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        board.initGame(scanner);
        board.playermove(scanner);
        
        
    }
}