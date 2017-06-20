package mvcexample;

/**
 * Created by tomwi on 20.06.2017.
 */
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

class SineDraw extends JPanel {
    static final int SCALEFACTOR = 200;
    int cycles;
    int points;
    double[] sines;
    int[] pts;

    SineDraw() {
        setCycles(5);
    }

    public void setCycles(int newCycles) {
        cycles = newCycles;
        points = SCALEFACTOR * cycles * 2;
        sines = new double[points];
        pts = new int[points];
        for (int i = 0; i < points; i++) {
            double radians = (Math.PI / SCALEFACTOR) * i;
            sines[i] = Math.sin(radians);
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); // in Superklasse aufrufen ...
        int maxWidth = getWidth(); // Weite bestimmen
        double hstep = (double) maxWidth / (double) points; // horizontale Schrittweite
        int maxHeight = getHeight(); // Hoehe Bestimmen
        for (int i = 0; i < points; i++) // fuer alle Punkte:
            pts[i] = (int) ((0.5 - sines[i] * 0.48) * maxHeight); // skalieren
        g.setColor(Color.red);
        for (int i = 1; i < points; i++) {
            int x1 = (int) ((i - 1) * hstep);
            int x2 = (int) (i * hstep);
            int y1 = pts[i - 1];
            int y2 = pts[i]; //
            g.drawLine(x1, y1, x2, y2);
        }
    } // end paintComponent

    public static class SineWave extends JApplet {
        SineDraw sines = new SineDraw();
        JSlider cycles = new JSlider(1, 30, 5);

        public void init() {
            Container cp = getContentPane();
            cp.add(BorderLayout.CENTER, sines);
            cycles.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    sines.setCycles(
                            ((JSlider) e.getSource()).getValue());
                }
            });
            cp.add(BorderLayout.SOUTH, cycles);
        }
    }

}
