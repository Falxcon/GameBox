package sokoban;

import javax.swing.*;
import java.awt.*;
import java.util.Observer;

/**
 * Created by tomwi on 29.05.2017.
 */
public class View extends JInternalFrame implements Observer{
    Model model;
    Controller controller;
    JLabel[][] labelGrid;

    boolean firstUpdate;

    public View(){
        model = new Model();
        model.addObserver(this);
        setJMenuBar(initMenuBar());
        setSize(400, 300);
        setVisible(true);

        firstUpdate = true;
    }

    public void initGrid(int rows, int cols){
        getContentPane().setLayout(new GridLayout(rows, cols));
        labelGrid = new JLabel[rows][cols];

        for(int y = 0; y < rows; y++){
            for(int x = 0; x < cols; x++){
                labelGrid[y][x] = new JLabel();
                labelGrid[y][x].setVisible(true);
                getContentPane().add(labelGrid[y][x]);
            }
        }
    }

    public JMenuBar initMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menuAdd = new JMenu("Map");
        menuBar.add(menuAdd);

        JMenuItem menuItemStartTest = new JMenuItem("test Map");
        menuItemStartTest.addActionListener(l -> {
            firstUpdate = true;
            model.loadMap("test");
        });
        menuAdd.add(menuItemStartTest);

        return menuBar;
    }


    @Override
    public void update(java.util.Observable o, Object arg) {
        if(firstUpdate){
            initGrid(model.getWidth(), model.getHeight());
            firstUpdate = false;
        }

        //Field[][] board = model.getBoard();

        for(int y = 0; y < model.getWidth(); y++){
            for(int x = 0; x < model.getHeight(); x++){

                //Field field = board[y][x];
                JLabel label = labelGrid[y][x];

                switch(model.getFieldByCoords(y, x)){
                    case WALL:
                        label.setText("W");
                        break;
                    case EMPTY:
                        label.setText("E");
                        break;
                    case PLAYER:
                        label.setText("P");
                        break;
                    case TARGET:
                        label.setText("T");
                        break;
                    case OBJECT:
                        label.setText("O");
                        break;
                    case OOT:
                        label.setText("*");
                        break;
                    case POT:
                        label.setText("+");
                        break;
                    default:
                        label.setText("?");

                }


            }
        }
    }
}
