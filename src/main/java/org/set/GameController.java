package org.set;

import java.awt.Color;
import java.util.List;

import org.set.boardPieces.HexagonGameBoard;
import org.set.boardPieces.Tile;
import org.set.boardPieces.Util;
import org.set.cards.Card;
import org.set.cards.expedition.ExpeditionCard;


public class GameController {
    private HexagonGameBoard board;
    private List<Player> players;
    private String GameState;
    public int turnCounter;
    public GameController(HexagonGameBoard board) {
        this.board=board;
        GameState="Game Starts";
        //Before_game.setSpecialColor(board);
        players=Before_game.addPlayer(board);
        //During_game.removeblock(board);
        Before_game.placePlayersOnBoard(board);
        GameSession();
        
    }
    
    private String displayGameState() {
        // Display the current state of the game, including the board and player positions
        return GameState;
    }
    
    private void PlayerDrawCards(int turn,int currentPlayerIndex) {

	    System.out.println("Player " + (currentPlayerIndex+1) + " drawing cards" );
    	Player currentPlayer = players.get(currentPlayerIndex);
    	List<Card> drawedCards=currentPlayer.mydeck.draw(5);
	    board.PlayerCards=drawedCards;//need to change, should be player's current deck, which needs to be added in cardDeck
        board.repaint();
    }
    
    public void PlayerMove(Player player) {
    	//Phase 1
    	//play cards that you want to use for moving
    	
    	List<Card> currentDeck=player.mydeck.getCurrentDeck();
    	boolean conditionNotMet=true;
    	while(conditionNotMet) {
    	int cardIndex = InputHelper.getIntInput("Choose 1 card for Movement, input index (e.g. 0)");
	    if (cardIndex>=currentDeck.size()) {
	        System.out.println("Please enter a number between 0 to "+(player.mydeck.getCurrentDeck().size()-1));
	        continue;
	    }
    	Card selectedCard=currentDeck.get(cardIndex);//only expedition card can move?
    	if (!(selectedCard instanceof ExpeditionCard)) {
            System.out.println("Please select an Expedition card.");
            continue;
        }
    	ExpeditionCard expeditionCard = (ExpeditionCard) selectedCard;
    	System.out.println("Where do you want to move with this card?");
    	String targetKey = player.getCurrentRow() + "," + player.getCurrentCol();
        Tile PlayerStandingTile = board.ParentMap.get(targetKey);
    	Tile MovingTo = InputHelper.getPlayerMoveInput(board,PlayerStandingTile);
    	Color cardcolor=Util.getColorFromString(selectedCard.cardType.toString());
    	if(cardcolor.equals(MovingTo.getColor())) {
    		if(expeditionCard.getPower()>=MovingTo.getPoints()) {
    			player.setPlayerPosition(MovingTo.getRow(), MovingTo.getCol());
    		}
    	}
    	}
    }
    public void PlayerBuy(Player player) {
    	//Phase 1
    	//Use the rest of your cards to buy up to 1 new card per turn.
    }
    public void PlayerDiscard(Player player) {
    	//Phase 2
    	//Discard Played Cards, keep card in your hand for your next turn
    	//decide for each card individually.
    }
    public void PlayerDrawCard(Player player) {
    	//Phase 3
    	//draw cards from your draw pile until you have 4 cards in your hand.
    	//If your draw pile doesn't contain enough cards to draw for your next turn, draw as many as possible. 
    	//Then, shuffle your discard pile to form your new draw pile, then draw the rest of the cards you need.
    }
    public void GameSession() {
        int turnNumber = 0;
        while (true) {
            for (int currentPlayerIndex = 0; currentPlayerIndex < players.size(); currentPlayerIndex++) {
            Player currentPlayer = players.get(currentPlayerIndex);
            System.out.println("Turn " + turnNumber + ": Player " + (currentPlayerIndex+1) + "'s turn.");
    	    PlayerDrawCards(turnNumber,currentPlayerIndex);
    	    
    	    int[] position = InputHelper.getPositionInput(board);
    	    
    	    currentPlayer.setPlayerPosition(position[0], position[1]);
	        board.repaint();
	        
            }

    	    turnNumber++;
            // Check for end conditions or other game logic
            if (isGameOver()) {
                break;
            }
        }

        // Game over, perform any cleanup or display final results
        endGame();
    }

    
    
    public void FinalRound() {
    	//1 player reaches WinningPiece, triggers final round
    	//Each player left in that round will now play their final turn
    	GameState="Final Round";
    }

    private boolean isGameOver() {
        // when the final round completes, the game is over
        return false; 
    }

    private void endGame() {
    	InputHelper.closeScanner();
        System.out.println("Game Over!");
    }
}

