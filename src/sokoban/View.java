package sokoban;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Observer;

/**
 * Created by tomwi on 29.05.2017.
 */
public class View extends JInternalFrame implements Observer{
    Model model;
    Controller controller;
    JLabel[][] labelGrid;

    int labelSize = 40;
    boolean firstUpdate;

    public View(){
        model = new Model();
        model.addObserver(this);
        setJMenuBar(initMenuBar());
        setSize(400, 300);
        setVisible(true);

        firstUpdate = true;
    }

    public void initGrid(int cols, int rows){
        setSize(cols * labelSize, (rows + 1) * labelSize);
        getContentPane().setLayout(new GridLayout(rows, cols));
        labelGrid = new JLabel[cols][rows];

        for(int y = 0; y < rows; y++){
            for(int x = 0; x < cols; x++){
                labelGrid[x][y] = new JLabel();
                labelGrid[x][y].setVisible(true);
                //labelGrid[x][y].setSize(labelSize, labelSize);
                getContentPane().add(labelGrid[x][y]);
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

        for(int y = 0; y < model.getHeight(); y++){
            for(int x = 0; x < model.getWidth(); x++){

                JLabel label = labelGrid[x][y];
                try {
                    switch (model.getFieldByCoordinate(x, y)) {
                        case WALL:
                            label.setIcon(new ImageIcon(ImageIO.read(new File("pictures/sokWall.png"))));
                            break;
                        case EMPTY:
                            label.setIcon(new ImageIcon(ImageIO.read(new File("pictures/sokEmpty.png"))));
                            break;
                        case PLAYER:
                            label.setIcon(new ImageIcon(ImageIO.read(new File("pictures/sokPlayer.png"))));
                            break;
                        case TARGET:
                            label.setIcon(new ImageIcon(ImageIO.read(new File("pictures/sokTarget.png"))));
                            break;
                        case OBJECT:
                            label.setIcon(new ImageIcon(ImageIO.read(new File("pictures/sokObject.png"))));
                            break;
                        case OOT:
                            label.setIcon(new ImageIcon(ImageIO.read(new File("pictures/sokOOT.png"))));
                            break;
                        case POT:
                            label.setIcon(new ImageIcon(ImageIO.read(new File("pictures/sokPOT.png"))));
                            break;
                        default:
                            label.setText("?");

                    }
                } catch (IOException e) {
                    System.out.println("picture not found");
                }


            }
        }
    }
}
