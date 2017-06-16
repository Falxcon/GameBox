package sokoban;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Observer;

public class View extends JInternalFrame implements Observer{
    Model model;
    JLabel[][] labelGrid;
    MoveAction moveUp, moveDown, moveRight, moveLeft;

    int labelSize = 40;
    boolean firstUpdate;

    public View(){
        model = new Model();
        model.addObserver(this);
        setJMenuBar(initMenuBar());
        setSize(400, 300);
        setVisible(true);

        initKeyBinds();

        firstUpdate = true;
    }

    private void initGrid(int cols, int rows){
        getContentPane().removeAll();
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

    private JMenuBar initMenuBar(){
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

    class MoveAction extends AbstractAction {
        int dx, dy;
        MoveAction(int dx, int dy){
            this.dx = dx;
            this.dy = dy;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            model.movePlayer(dx, dy);
        }
    }

    private void initKeyBinds(){
        moveUp = new MoveAction(0, -1);
        moveDown = new MoveAction(0, 1);
        moveRight = new MoveAction(1, 0);
        moveLeft = new MoveAction(-1, 0);
        getInputMap().put(KeyStroke.getKeyStroke("UP"), "moveUp");
        getActionMap().put("moveUp", moveUp);
        getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        getActionMap().put("moveDown", moveDown);
        getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        getActionMap().put("moveRight", moveRight);
        getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        getActionMap().put("moveLeft", moveLeft);
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
                            label.setIcon(new ImageIcon(ImageIO.read(new File("src/sokoban/pictures/sokWall.png"))));
                            break;
                        case EMPTY:
                            label.setIcon(new ImageIcon(ImageIO.read(new File("src/sokoban/pictures/sokEmpty.png"))));
                            break;
                        case PLAYER:
                            label.setIcon(new ImageIcon(ImageIO.read(new File("src/sokoban/pictures/sokPlayer.png"))));
                            break;
                        case TARGET:
                            label.setIcon(new ImageIcon(ImageIO.read(new File("src/sokoban/pictures/sokTarget.png"))));
                            break;
                        case OBJECT:
                            label.setIcon(new ImageIcon(ImageIO.read(new File("src/sokoban/pictures/sokObject.png"))));
                            break;
                        case OOT:
                            label.setIcon(new ImageIcon(ImageIO.read(new File("src/sokoban/pictures/sokOOT.png"))));
                            break;
                        case POT:
                            label.setIcon(new ImageIcon(ImageIO.read(new File("src/sokoban/pictures/sokPOT.png"))));
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
