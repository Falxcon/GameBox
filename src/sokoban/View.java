package sokoban;

import javax.swing.*;
import java.util.Observer;

/**
 * Created by tomwi on 29.05.2017.
 */
public class View extends JInternalFrame implements Observer{
    Model model;
    Controller controller;
    JMenuBar menuBar;

    View(){


    }


    @Override
    public void update(java.util.Observable o, Object arg) {
        Field[][] board = model.getBoard();

    }
}
