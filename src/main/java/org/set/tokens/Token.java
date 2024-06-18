package org.set.tokens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

import org.set.boardPieces.Util;
import org.set.cards.CardActionHandler;
import org.set.cards.action.ActionCardType;
import org.set.cards.expedition.ExpeditionCardType;
import org.set.player.Asset;
import org.set.player.Player;
import org.set.cards.CardType;

public class Token extends Asset{
    
    private CardType cardType;
    private CaveTokenType caveTokenType;
    private int power;

    public Token(CaveTokenType caveTokenType) {
        this.cardType = caveTokenType.getCardType();
        this.caveTokenType = caveTokenType;
        this.power = caveTokenType.getPower();
        
    }
    
    public CaveTokenType getType() {
        return caveTokenType;
    }
    public void useToken(Player player) {
        TokenActionHandler actionHandler = new TokenActionHandler();
        actionHandler.doAction(this, player);
    }
    
    @Override
    public String toString() {
        return "Token{" +
                "cardType=" + cardType +
                ", caveTokenType=" + caveTokenType +
                ", power=" + power +
                '}';
    }
    
    @Override
    public void draw(Graphics2D g2d, int x, int y, int width, int height) {
		Color color = Util.getColorFromString(cardType.toString());
		Color transparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 100);
		g2d.setColor(transparentColor);
		int radius = Math.min(width, height) / 2; 
		int centerX = x + width / 2;
	    int centerY = y + height / 2;
	    int[] xPoints = new int[6];
	    int[] yPoints = new int[6];

	    for (int i = 0; i < 6; i++) {
	        xPoints[i] = centerX + (int) (radius * Math.cos(Math.toRadians(60 * i - 30)));
	        yPoints[i] = centerY + (int) (radius * Math.sin(Math.toRadians(60 * i - 30)));
	    }

	    g2d.fillPolygon(xPoints, yPoints, 6);
	    g2d.setColor(Color.BLACK);
	    g2d.drawPolygon(xPoints, yPoints, 6);
		    
		    if (power != 0) {
		        g2d.drawString("Power: " + power, x + width / 2 - 15, y + height / 2 + 10);
		    }
		    
	}
    
    public CardType getCardType() {
    	return cardType;
    }
    
    @Override
	public double getValue() {
		if(caveTokenType==CaveTokenType.CoinOne||caveTokenType==CaveTokenType.CoinTwo) {
			return power;
		}else {
		return 0;}
	}
    
    public int getPower() {
        return power;
    }
    
	@Override
	public String getName() {
		
		return caveTokenType.toString();
	}
}
