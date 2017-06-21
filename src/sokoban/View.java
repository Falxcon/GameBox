package sokoban;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Observer;

/**
 * Created by tomwi on 29.05.2017.
 */
public class View extends JInternalFrame implements Observer{
    Model model;
    JLabel[][] labelGrid;
    MoveAction moveUp, moveDown, moveRight, moveLeft;

    int labelSize;
    final int standardLabelSize = 40;

    public View(){
        super("Sokoban", false, true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        model = new Model(this);
        model.addObserver(this);
        setJMenuBar(initMenuBar());
        setSize(400, 300);
        setVisible(true);

        initKeyBinds();
    }

    public void initGrid(int cols, int rows){
        labelSize = standardLabelSize;
        getContentPane().removeAll();
        setSize(cols * labelSize, (rows + 1) * labelSize);
        while(getWidth() > getParent().getWidth() || getHeight() > getParent().getHeight()){
            labelSize--;
            setSize(cols * labelSize, (rows + 1) * labelSize);
        }
        getContentPane().setLayout(new GridLayout(rows, cols));
        labelGrid = new JLabel[cols][rows];

        for(int y = 0; y < rows; y++){
            for(int x = 0; x < cols; x++){
                labelGrid[x][y] = new JLabel();
                labelGrid[x][y].setVisible(true);
                getContentPane().add(labelGrid[x][y]);
            }
        }
    }

    private JMenuBar initMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menuAdd = new JMenu("Map");
        JMenu menuAction = new JMenu("Action");
        menuBar.add(menuAdd);
        menuBar.add(menuAction);

        File folder = new File("src/sokoban/maps");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String fileName = listOfFiles[i].getName();
                JMenuItem menuItemMap = new JMenuItem(fileName.substring(0, fileName.indexOf('.')));
                menuItemMap.addActionListener(l -> {
                    model.loadMap(fileName);
                });
                menuAdd.add(menuItemMap);
            }
        }

        JMenuItem menuItemUndo = new JMenuItem("Undo");
        menuItemUndo.addActionListener(l -> {
            model.undo();
        });
        menuAction.add(menuItemUndo);

        JMenuItem menuItemRestart = new JMenuItem("Restart");
        menuItemRestart.addActionListener(l -> {
            model.restart();
        });
        menuAction.add(menuItemRestart);

        JMenuItem menuItemPreviousLevel = new JMenuItem("Previous Level");
        menuItemPreviousLevel.addActionListener(l -> {
            model.previousLevel();
        });
        menuAction.add(menuItemPreviousLevel);

        JMenuItem menuItemNextLevel = new JMenuItem("Next Level");
        menuItemNextLevel.addActionListener(l -> {
            model.nextLevel();
        });
        menuAction.add(menuItemNextLevel);

        JMenuItem menuItemSaveGame = new JMenuItem("Save");
        menuItemSaveGame.addActionListener(l -> {
            model.saveGame();
        });
        menuAction.add(menuItemSaveGame);

        JMenuItem menuItemLoadGame = new JMenuItem("Load");
        menuItemLoadGame.addActionListener(l -> {
            model.loadGame("test");
        });
        menuAction.add(menuItemLoadGame);

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

        for(int y = 0; y < model.getHeight(); y++){
            for(int x = 0; x < model.getWidth(); x++){
                JLabel label = labelGrid[x][y];
                try {
                    switch (model.getFieldByCoordinate(x, y)) {
                        case WALL:
                            label.setIcon(new ImageIcon(ImageIO.read(new File("src/sokoban/pictures/sokWall.png")).getScaledInstance(labelSize, labelSize, Image.SCALE_DEFAULT)));
                            break;
                        case EMPTY:
                            label.setIcon(new ImageIcon(ImageIO.read(new File("src/sokoban/pictures/sokEmpty.png")).getScaledInstance(labelSize, labelSize, Image.SCALE_DEFAULT)));
                            break;
                        case PLAYER:
                            label.setIcon(new ImageIcon(ImageIO.read(new File("src/sokoban/pictures/sokPlayer.png")).getScaledInstance(labelSize, labelSize, Image.SCALE_DEFAULT)));
                            break;
                        case TARGET:
                            label.setIcon(new ImageIcon(ImageIO.read(new File("src/sokoban/pictures/sokTarget.png")).getScaledInstance(labelSize, labelSize, Image.SCALE_DEFAULT)));
                            break;
                        case OBJECT:
                            label.setIcon(new ImageIcon(ImageIO.read(new File("src/sokoban/pictures/sokObject.png")).getScaledInstance(labelSize, labelSize, Image.SCALE_DEFAULT)));
                            break;
                        case OOT:
                            label.setIcon(new ImageIcon(ImageIO.read(new File("src/sokoban/pictures/sokOOT.png")).getScaledInstance(labelSize, labelSize, Image.SCALE_DEFAULT)));
                            break;
                        case POT:
                            label.setIcon(new ImageIcon(ImageIO.read(new File("src/sokoban/pictures/sokPOT.png")).getScaledInstance(labelSize, labelSize, Image.SCALE_DEFAULT)));
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
