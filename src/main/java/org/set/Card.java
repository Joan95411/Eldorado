package org.set;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Card {
	public int id;
	public Color color;
	public double buyingValue;
	public double worthValue;
	private static final int width=80;
	private static final int height=120;
	
	 public Card(int id,Color color,double d,double worth) {
	        this.id=id;
	        this.color=color;
	        this.buyingValue=d;
	        this.worthValue=worth;
	    }
	 
	public void draw(Graphics2D g2d, int x, int y) {
		Color transparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 100);
        g2d.setColor(transparentColor);
        g2d.fillRect(x, y, width, height);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, width, height);
        int fontSize = 15; // Adjust as needed
        Font font = new Font("Arial", Font.BOLD, fontSize); 
        g2d.setFont(font);
        g2d.drawString("Buy: "+buyingValue, x+5, y+15);
        g2d.drawString("Index: "+id, x+5, y+(height-20)/2);
        g2d.drawString("Worth: "+worthValue, x+5, y+height-5);
    }
}
