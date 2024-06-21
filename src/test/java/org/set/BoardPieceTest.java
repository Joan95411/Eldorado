package org.set;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.set.player.Player;
import org.set.template.Team03Board;
import org.set.template.Template;

class BoardPieceTest {

	private Template board;
    private static InputStream backupInputStream;
    
    @BeforeAll
    public static void start() {
       
        backupInputStream = System.in;
    }
    @BeforeEach
    public void setUp() {
    	board = new Team03Board(35, 35, 25); 
        Random random = new Random();
    }

}
