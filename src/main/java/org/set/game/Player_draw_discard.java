package org.set.game;

import org.set.cards.CardType;
import org.set.cards.action.ActionCard;
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
    	while(player.myDeck.isThereActionAsset()>=0){
        	boolean toPlay=InputHelper.getYesNoInput("Do you want to play action cards/token?");
        	if(!toPlay) {break;}
        	int cardIndex = InputHelper.getIntInput("Choose 1 action card/token to play;"
        			+ " Input index (e.g. "+player.myDeck.isThereActionAsset()+");"
        					+ " or '-1' to stop",player.myDeck.getMyasset().size()-1,-1);
	        if (cardIndex == -1) {
	            break;
	        }
	        Asset selected=player.myDeck.getMyasset().get(cardIndex);
	        if ((selected instanceof ActionCard)) {
	        	ActionCard actionCard=(ActionCard) selected;
		        actionCard.doAction(player);
		        if(actionCard.singleUse) {
		        	player.myDeck.removeCard(actionCard);
		        }else {
		        	player.myDeck.discard(actionCard);
		        }
		        board.repaint();
		        continue;
		        }
	        else if((selected instanceof Token)) {
	        	Token selectedToken=(Token)selected;
	        	System.out.println("You chose"+selectedToken.getName());
	        	if(selectedToken.getCardType()==CardType.PURPLE) {
	        		selectedToken.useToken(player);
	        		player.myDeck.discardAsset(cardIndex);
	        		board.repaint();
			        continue;
	        }else{System.out.println("This is not an Action token.");}
	        }
	        else{System.out.println("Please select a Action card or token.");
		        continue;
		    }
	        
//	        player.myDeck.discardFromHand(actionIndex);
	        
        }
    }
}
