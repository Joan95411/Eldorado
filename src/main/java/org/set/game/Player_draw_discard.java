package org.set.game;

import org.set.cards.CardType;
import org.set.cards.action.ActionCard;
import org.set.player.Actionable;
import org.set.player.Asset;
import org.set.player.Player;
import org.set.template.Template;
import org.set.tokens.Token;

public class Player_draw_discard {
	
	public static void PlayerDrawCards(Template board,Player player) {
    	board.currentPlayer=player;
        System.out.println("Player " + player.getName() + " drew cards");
        player.myDeck.draw(4 - player.myDeck.getCardsInHand().size());
        board.repaint();
    }
	
	public static void PlayerDiscard(Template board,Player player) {
		
    	board.currentPlayer=player;
        while(player.myDeck.getCardsInHand().size()>0){
        	boolean discardCard=InputHelper.getYesNoInput("Do you want to discard any cards?");
        	if(!discardCard) {break;}
        	int cardIndex = InputHelper.getIntInput("Choose 1 card to discard;"
        			+ " Input index (e.g. 0);"
        			+ "Or '-1' to stop discarding",player.myDeck.getCardsInHand().size()-1,-1);
	        if (cardIndex == -1) {
	            break;
	        }
	        player.myDeck.discardFromHand(cardIndex);
	        board.repaint();
        }
        
    }
	
	public static void PlayActionCard(Template board,Player player) {
    	board.currentPlayer=player;
    	while(player.myDeck.isThereActionAsset().size()>0){    		
        	boolean toPlay=InputHelper.getYesNoInput("Do you want to play action cards/token?");
        	if(!toPlay) {break;}
        	int cardIndex=player.myDeck.isThereActionAsset().getFirst();
        	if(player.myDeck.isThereActionAsset().size()>1) {
        	cardIndex = InputHelper.getIntInput("Choose 1 action card/token to play;"
        			+ " Input index (e.g. 0);"
        					+ " or '-1' to stop",player.myDeck.getMyasset().size()-1,-1);
	        if (cardIndex == -1) {
	            break;
	        }}
	        Asset selected=player.myDeck.getMyasset().get(cardIndex);
	        if (!(selected instanceof Token) && !(selected instanceof ActionCard)) {
	            System.out.println("Please select an Action card or token.");
	            continue;
	        }

	        if (selected instanceof Token) {
	            Token token = (Token) selected;
	            if (token.getCardType() != CardType.PURPLE) {
	                System.out.println("Please select an Action card or a purple token.");
	                continue;
	            }
	        }
	        Actionable actionable=(Actionable)selected;
	        actionable.doAction(player);
	        board.repaint();
        }
    }
}
