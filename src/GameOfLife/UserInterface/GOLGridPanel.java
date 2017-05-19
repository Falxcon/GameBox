package GameOfLife.UserInterface;

import GameOfLife.Model.GOLSimulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

public class GOLGridPanel extends JPanel implements Observer{

    private int cellSize;
    private boolean draw;

    private Color dead, alive;
    private GOLSimulation simulation;

    public GOLGridPanel(GOLSimulation simulation) {
        cellSize = 10;
        draw = false;
        dead = Color.BLACK;
        alive = Color.GREEN;

        this.simulation = simulation;

        addMouseListener(new Listener());
        addMouseMotionListener(new Listener());
    }

    /**
     * wechselt zwischen malen (true) und setzen (false)
     */
    public void switchDrawMode() {
        draw = !draw;
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int x = 0; x < simulation.getGrid().length; x++) {
            for (int y = 0; y < simulation.getGrid()[0].length; y++) {
                if(simulation.getGrid()[x] [y]) {
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
        public PopupMenu(){
            // change the color of dead Cells
            JMenuItem popupDead = new JMenuItem("Change dead cell color");
            popupDead.addActionListener(e -> {
                dead = JColorChooser.showDialog(this, "Dead", dead);
                GOLGridPanel.this.repaint();
            });
            add(popupDead);
            // change the color of alive cells
            JMenuItem popupAlive = new JMenuItem("Change alive cell color");
            popupAlive.addActionListener(e -> {
                alive = JColorChooser.showDialog(this, "Alive", alive);
                GOLGridPanel.this.repaint();
            });
            add(popupAlive);
        }
    }

    class Listener extends MouseAdapter {
        // setzen
        @Override
        public void mousePressed(MouseEvent e) {
            if (!draw) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    doUpdate(e);
                }
            }
        }

        // malen
        @Override
        public void mouseDragged(MouseEvent e) {
            if (draw) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    doUpdate(e);
                }
            }
        }

        // aktualisiert die simulation an der Position der Maus
        private void doUpdate(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            if (x > 0 && x < simulation.getGrid().length * cellSize && y > 0 && y < simulation.getGrid()[0].length * cellSize) {
                simulation.setCell(x / cellSize, y / cellSize, true);
                GOLGridPanel.this.repaint();
            }
        }

        // öffnet neues PopupMenu bei einem rechtklicks
        @Override
        public void mouseReleased(MouseEvent e){
            if (SwingUtilities.isRightMouseButton(e)) {
                doPopup(e);
            }
        }

        // erstellt neues popupMenu an der maus position
        private void doPopup(MouseEvent e){
            PopupMenu popupMenu = new PopupMenu();
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
