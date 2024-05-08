package org.set;

import java.awt.*;
import javax.swing.*;
import java.util.Scanner;

public class HexagonGameBoard extends JPanel {
    private int numRows;
    private int numCols;
    private int hexSize;
    private int playerRow;
    private int playerCol;

    public HexagonGameBoard(int numRows, int numCols, int hexSize) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.hexSize = hexSize;
        setPreferredSize(new Dimension((int) (numCols * 1.5 * hexSize), (int) (numRows * Math.sqrt(3) * hexSize)));
    }

    public void setPlayerPosition(int row, int col) {
        this.playerRow = row;
        this.playerCol = col;
        repaint(); // Redraw the board with the updated player position
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int x = col * (int) (1.5 * hexSize);
                int y = row * (int) (Math.sqrt(3) * hexSize);
                if (col % 2 == 1) {
                    y += (int) (Math.sqrt(3) / 2 * hexSize);
                }
                drawHexagon(g2d, x, y, hexSize, Color.GRAY,row,col);
            }
        }
        drawPlayer(g2d, playerRow, playerCol);
    }


    private void drawHexagon(Graphics2D g2d, int x, int y, int size, Color color, int row, int col) {
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];
        for (int i = 0; i < 6; i++) {
            xPoints[i] = (int) (x + size * Math.cos(i * Math.PI / 3));
            yPoints[i] = (int) (y + size * Math.sin(i * Math.PI / 3));
        }
        Color transparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 150); // Adjust the alpha value (0-255) as needed

        g2d.setColor(transparentColor);
        g2d.fillPolygon(xPoints, yPoints, 6);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(xPoints, yPoints, 6);

        // Draw row and column numbers
        FontMetrics fm = g2d.getFontMetrics();
        String rowColStr = row + "," + col;
        int rowColWidth = fm.stringWidth(rowColStr);
        int rowColHeight = fm.getHeight();
        int centerX = x + size-25;
        int centerY = y + size-45;
        g2d.drawString(rowColStr, centerX - rowColWidth / 2, centerY + rowColHeight / 2);
    }
    private void drawPlayer(Graphics2D g2d, int row, int col) {
        // Calculate center of hexagon
        int x = col * (int) (1.5 * hexSize);
        int y = row * (int) (Math.sqrt(3) * hexSize);
        if (col % 2 == 1) {
            y += (int) (Math.sqrt(3) / 2 * hexSize);
        }
        int centerX = x + hexSize-90;
        int centerY = y + hexSize-45;

        // Draw a star representing player's position
        int[] xPoints = {centerX, centerX + hexSize / 4, centerX + hexSize / 2, centerX + hexSize * 3 / 4, centerX + hexSize, centerX + hexSize * 3 / 4, centerX + hexSize / 2, centerX + hexSize / 4};
        int[] yPoints = {centerY - hexSize / 4, centerY - hexSize / 4, centerY - hexSize / 2, centerY - hexSize / 4, centerY - hexSize / 4, centerY, centerY + hexSize / 4, centerY - hexSize / 4};
        g2d.setColor(Color.RED);
        g2d.fillPolygon(xPoints, yPoints, 8);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hexagon Game Board");
        HexagonGameBoard board = new HexagonGameBoard(10, 10, 50);
        frame.add(board);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        board.setPlayerPositionFromUserInput();
    }
    public void setPlayerPositionFromUserInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter row and column for player's position (e.g., '2 3'), or type 'stop' to exit:");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("stop")) {
                break;
            }
            String[] tokens = input.split("\\s+");
            if (tokens.length != 2) {
                System.out.println("Invalid input. Please enter row and column separated by space.");
                continue;
            }
            try {
                int row = Integer.parseInt(tokens[0]);
                int col = Integer.parseInt(tokens[1]);
                setPlayerPosition(row, col);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter valid integers for row and column.");
            }
        }
        scanner.close();
    }
}

