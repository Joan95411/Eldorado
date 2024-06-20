package org.set.game;

import javax.swing.*;
import org.set.template.Team03Board;
import org.set.template.Team04Board;
import org.set.template.Template;

public class EldoradoGame implements Runnable {

   public Template board;
   public JFrame frame;
   public GameController gameControl;

    @Override
    public void run() {
        frame = new JFrame("Hexagon Game Board");
        boolean setupChange=InputHelper.getYesNoInput("Do you want to use Team3's set up?");
        if(setupChange) {
            board=new Team03Board(35, 35, 25);
        }else {
            board=new Team04Board(25, 30, 25);
        }
        JScrollPane scrollPane = new JScrollPane(board);
        frame.add(scrollPane);
        frame.setSize(1250, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        gameControl = new GameController(board);
        gameControl.GameSession();
        //        frame.dispose();
    }

}
