package org.set.tokens;

import java.util.ArrayList;
import java.util.List;

import org.set.boardPieces.Tile;

public class Cave {
	public Tile tile;
	public List<Token> tokens;
	
	public Cave(Tile tile){
		this.tile=tile;
		this.tokens= new ArrayList<>();
	}
	public void addToken(Token token) {
        if (tokens.size() < 4) {
            tokens.add(token);
        } else {
            System.out.println("Overflow of tokens");
        }
    }
	public List<Token> getTokens() {
        return tokens;
    }
	
	public Token getAtoken() {
		if(tokens.size()>0) {
			Token token = tokens.get(0);
	        tokens.remove(0); // Remove the token from the list
	        return token;}
		else {
			System.out.println("No more tokens here.");
			return null;
		}
	}
}
