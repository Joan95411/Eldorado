package org.set;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.set.template.Team03Board;

class BoardPieceTest {

	@Test
	void test() {
		Team03Board Board = new Team03Board(35, 35, 25);
		 Board.boardPieces.clear();
	}

}
