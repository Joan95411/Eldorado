package org.set.boardPieces;

import org.set.player.Player;

import org.set.cards.Card;

import java.awt.*;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class HexagonGameBoard extends JPanel {
    public int numRows;
    public int numCols;
    public int hexSize;
    public int cardWidth;
    public int cardHeight;
    public List<Player> players;
    public Map<String, Tile> tilesMap;
    public Map<String, Tile> ParentMap;
    public Map<String, BoardPiece> boardPieces;
    public List<int[]> coordinateList;
    public List<Card> PlayerCards;

    public HexagonGameBoard(int numRows, int numCols, int hexSize) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.hexSize = hexSize;
        this.cardWidth = hexSize * 2;
        this.cardHeight = cardWidth / 2 * 3;
        setPreferredSize(new Dimension((int) (numCols * 1.5 * hexSize), (int) (numRows * Math.sqrt(3) * hexSize)));
        tilesMap = new HashMap<>();
        ParentMap = new HashMap<>();
        boardPieces = new HashMap<>();
        Terrain.resetWinningCount();
        Blockade.resetCount();
        WinningPiece.resetCount();
        coordinateList = Arrays.asList(
                new int[] { 6, 4 },
                new int[] { 0, 8 },
                new int[] { -6, 4 }
        // new int[]{0, 8}
        );
        loadTileData();
        initBoard();
    }

    public void initBoard() {
        int maxIndex = 0;
        List<Terrain> terrains = getAllTerrains();
        
        for (Terrain terrain : terrains) {
            String name = terrain.getName();
            int index = Integer.parseInt(name.substring("Terrain_".length()));

            // Check if this index is greater than the current maxIndex
            if (index > maxIndex) {
                maxIndex = index;
            }

        }

        for (int[] coordinates : coordinateList) {
            Terrain modelter = (Terrain) boardPieces.get("Terrain_" + maxIndex);
            addTerrain(coordinates[0], coordinates[1], modelter);
            maxIndex++;
        }

    }

    public void loadTileData() {
        try {
            TileDataDic tdd = new TileDataDic(numRows, numCols, hexSize);
            boardPieces.put(tdd.terrainA.getName(), tdd.terrainA);
            boardPieces.put(tdd.wpa.getName(), tdd.wpa);
            this.tilesMap = tdd.tilesMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (BoardPiece piece : boardPieces.values()) {
            for (Tile tile : piece.getTiles()) {
                ParentMap.put(tile.getRow() + "," + tile.getCol(), tile);
            }
            piece.draw(g2d, hexSize, tilesMap);
        }

        if (players != null && players.size() > 0) {
            for (Player player : players) {
                player.draw(g2d, hexSize);
            }
        }

        if (PlayerCards != null) {
            drawPlayerDeck(g2d);
        }

    }

    public void drawPlayerDeck(Graphics2D g2d) {
        Tile temp = tilesMap.get("1,6");

        g2d.setColor(Color.BLACK);
        int fontSize = 12;
        Font font = new Font("Arial", Font.BOLD, fontSize);
        g2d.setFont(font);

        FontMetrics fm = g2d.getFontMetrics();
        String caption = "Current Player's deck: ";
        int captionWidth = fm.stringWidth(caption);
        g2d.drawString(caption, temp.getX(), temp.getY());
        int maxCardsPerRow = 4;
        int cardSpacing = cardWidth / 10;
        int totalCards = PlayerCards.size();
        int cardsDrawn = 0;

        for (int i = 0; i < totalCards; i++) {
            int row = i / maxCardsPerRow; // Calculate the row index
            int col = i % maxCardsPerRow; // Calculate the column index

            int x = temp.getX() + captionWidth + col * (cardWidth + cardSpacing);
            int y = 10 + row * (cardHeight + cardSpacing);

            PlayerCards.get(i).draw(g2d, x, y, cardWidth, cardHeight);
            g2d.drawString("Index: " + i, x + 5, y + cardHeight / 2);
            cardsDrawn++;

            if (cardsDrawn >= totalCards) {
                break;
            }
        }
    }

    public List<Terrain> getAllTerrains() {
        return boardPieces.keySet().stream()
                .filter(key -> key.startsWith("Terrain_"))
                .map(key -> (Terrain) boardPieces.get(key))
                .collect(Collectors.toList());
    }

    public List<Blockade> getAllBlockades() {
        return boardPieces.keySet().stream()
                .filter(key -> key.startsWith("Blockade_"))
                .map(key -> (Blockade) boardPieces.get(key))
                .collect(Collectors.toList());
    }

    public List<WinningPiece> getAllWinningPieces() {
        return boardPieces.keySet().stream()
                .filter(key -> key.startsWith("Winning_"))
                .map(key -> (WinningPiece) boardPieces.get(key))
                .collect(Collectors.toList());
    }

    public void addTerrain(int addRow, int addCol, Terrain terrainA) {
        List<WinningPiece> WP = getAllWinningPieces();
        addWinningPiece(addRow, addCol, WP.get(WP.size() - 1));
        Terrain terrainB = terrainA.clone(addRow, addCol, tilesMap);
        terrainB.randomizeTiles();
        boardPieces.put(terrainB.getName(), terrainB);
        Set<int[]> neighbors = terrainA.findOverlappingNeighbors(terrainB);
        if (neighbors.size() >= 3 && neighbors.size() <= 5) {
            Blockade blockade = new Blockade();

            for (int[] coordinate : neighbors) {
                int row = coordinate[0];
                int col = coordinate[1];
                Tile temp = tilesMap.get(row + "," + col);
                blockade.addTile(temp);
            }

            blockade.randomizeTiles();
            int indexA = Integer.parseInt(terrainA.getName().substring("Terrain_".length()));
            int indexB = Integer.parseInt(terrainB.getName().substring("Terrain_".length()));
            blockade.setTerrainNeighbors(indexA, indexB);
            boardPieces.put(blockade.getName(), blockade);
        }
    }

    public void addWinningPiece(int addRow, int addCol, WinningPiece wpa) {
        WinningPiece wpb = wpa.clone(addRow, addCol, tilesMap);

        for (Tile tile : wpa.getTiles()) {
            tile.setParent(null);
        }

        boardPieces.remove(wpa.getName());
        boardPieces.put(wpb.getName(), wpb);
    }

    public void removeBlockade(int blockRemoveIndex) {
        Blockade blockRemove = (Blockade) boardPieces.get("Blockade_" + (blockRemoveIndex));
        if (blockRemove == null) {
            throw new IllegalArgumentException("Blockade with index " + blockRemoveIndex + " does not exist");
        }

        int indexTerrain = blockRemove.getTerrainNeighbors()[0];
        boardPieces.remove("Blockade_" + (blockRemoveIndex));
        int[] change;

        if (coordinateList.get(indexTerrain - 1)[0] > 0) {
            change = new int[] { -1, 0 }; // Move one unit up
        } else if (coordinateList.get(indexTerrain - 1)[0] < 0) {
            change = new int[] { 1, 0 }; // Move one unit down
        } else {
            change = new int[] { 0, -1 }; // Move one unit left
        }

        for (BoardPiece piece : boardPieces.values()) {
            if (piece.getName().startsWith("Terrain_")) {
                // Extract the substring after "Terrain_"
                String indexString = piece.getName().substring("Terrain_".length());
                int index = Integer.parseInt(indexString);
                if (index <= indexTerrain) {
                    continue;
                }
            }
            piece.move(change[0], change[1]);
        }
    }

    public boolean isValidPosition(int row, int col) {
        String targetKey = row + "," + col;
        Tile temp = ParentMap.get(targetKey);
        if (temp == null || temp.getParent() == null || temp.getParent().startsWith("Blockade_")) {
            System.out.println("This tile doesn't belong in any board piece or is a block.");
            return false;
        }

        if (players != null && players.size() > 0) {
            for (Player player : players) {
                if (player.isAtPosition(row, col)) {
                    System.out.println("Someone's already here. Choose another move!");
                    return false;
                }
            }
        }
        return true;
    }
}