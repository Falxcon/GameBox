package gameoflife.userinterface;

import gameoflife.model.Simulation;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GridPanelMain extends GridPanelBasic {

    private boolean draw;

    public GridPanelMain(Simulation simulation) {
        super(simulation);

        draw = false;

        addMouseListener(new Listener());
        addMouseMotionListener(new Listener());
    }

    /**
     * wechselt zwischen malen (true) und setzen (false)
     */
    public void switchDrawMode() {
        draw = !draw;
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
                repaint();
            }
        }
    }
}
