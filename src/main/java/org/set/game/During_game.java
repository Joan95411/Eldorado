package org.set.game;

import java.util.List;

import org.set.boardPieces.Blockade;
import org.set.boardPieces.HexagonGameBoard;

public class During_game {

	public static void removeblock(HexagonGameBoard board) {
		while (true) {
			boolean wantsToContinue = InputHelper.getYesNoInput("Do you want to remove block?");
			
			if (wantsToContinue == false) {
				break;
			}
			
			int minIndex = 100;
			List<Blockade> blocks = board.getAllBlockades();
			
			if (blocks.size() > 0) {
				for (Blockade block : blocks) {
					String name = block.getName();
					int index = Integer.parseInt(name.substring("Blockade_".length()));

					if (index < minIndex) {
						minIndex = index;
					}
				}
			} else {
				System.out.println("Sorry no more blockade left");
			}

			board.removeBlockade(minIndex);

			board.repaint();
		}
	}
}
