package gameoflife.userinterface;

import gameoflife.model.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

/*
* @author Valentin Lutz
* */

public class GridPanelBasic extends JPanel implements Observer {

    protected int cellSize;

    private Color dead, alive;
    protected Simulation simulation;

    public GridPanelBasic(Simulation simulation) {
        cellSize = 10;
        dead = Color.BLACK;
        alive = Color.GREEN;

        this.simulation = simulation;

        addMouseListener(new Listener());
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int xGridLenght = simulation.getGrid().length;
        int yGridLenght = simulation.getGrid()[0].length;

        for (int x = 0; x < xGridLenght; x++) {
            for (int y = 0; y < yGridLenght; y++) {
                if (simulation.getGrid()[x][y]) {
                    g.setColor(alive);
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                } else {
                    g.setColor(dead);
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                }
            }
        }
    }

    public void increaseCellSize() {
        cellSize += 1;
        repaint();
    }

    public void decreaseCellSize() {
        if (cellSize > 1) {
            cellSize -= 1;
        }
        repaint();
    }

    public int getWidth() {
        return simulation.getGrid().length * cellSize;
    }

    public int getHeight() {
        return simulation.getGrid()[0].length * cellSize;
    }

    class PopupMenu extends JPopupMenu {
        public PopupMenu() {
            // change the color of dead Cells
            JMenuItem popupDead = new JMenuItem("Change dead cell color");
            popupDead.addActionListener(e -> {
                dead = JColorChooser.showDialog(this, "Dead", dead);
                GridPanelBasic.this.repaint();
            });
            add(popupDead);
            // change the color of alive cells
            JMenuItem popupAlive = new JMenuItem("Change alive cell color");
            popupAlive.addActionListener(e -> {
                alive = JColorChooser.showDialog(this, "Alive", alive);
                GridPanelBasic.this.repaint();
            });
            add(popupAlive);
        }
    }

    class Listener extends MouseAdapter {
        // öffnet neues PopupMenu bei einem rechtklicks
        @Override
        public void mouseReleased(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                doPopup(e);
            }
        }

        // erstellt neues popupMenu an der maus position
        private void doPopup(MouseEvent e) {
            PopupMenu popupMenu = new PopupMenu();
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
