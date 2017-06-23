package connect6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/*
* @author Valentin Lutz
* */

public class Connect6Panel extends JPanel {
    private final double STONE_SIZE = 0.9; // Spielsteingröße von 0,0 - 1,0 (0,9 entsprechen 90%)
    private int size; // Größe des Spielfeldes

    private Connect6Board connect6Board;

    public Connect6Panel(int size) {
        this.size = size;
        connect6Board = new Connect6Board(size);
        addMouseListener(new Connect6Panel.Connect6Listener());
    }

    public void resetPanel(int size) {
        this.size = size;
        connect6Board = new Connect6Board(size);
        repaint();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        double panelWidth = getWidth(); // Breite des Panels
        double panelHeight = getHeight(); // HÃ¶he des Panels
        double boardWidth = Math.min(panelWidth, panelHeight) - 2; // SpielbrettgrÃ¶ÃŸe
        double squareWidth = boardWidth / size; // GrÃ¶ÃŸe der Spielsteine
        double gridWidth = (size - 1) * squareWidth; // Gesamtbreite des Spielbrettes
        double StoneDiameter = STONE_SIZE * squareWidth; // Durchmesser der Spielsteine
        boardWidth = boardWidth - StoneDiameter;
        double xLeft = (panelWidth - boardWidth) / 2; // Setienabstand links ab dem das Spielfeld beginnt
        double yTop = (panelHeight - boardWidth) / 2; // Seitenabstand oben ab dem das Spielfeld beginnt
        // Hintergrundfarbe des Brettes
        g2.setColor(new Color(0.8156863f, 0.6f, 0.25882354f));
        g2.fill(new Rectangle2D.Double(0, 0, panelWidth, panelHeight));
        // Zeichne Gitter auf dem die Spielsteine liegen
        g2.setColor(Color.BLACK);
        for (int i = 0; i < size; i++) {
            double offset = i * squareWidth;
            g2.draw(new Line2D.Double(xLeft, yTop + offset,
                    xLeft + gridWidth, yTop + offset));
            g2.draw(new Line2D.Double(xLeft + offset, yTop,
                    xLeft + offset, yTop + gridWidth));
        }
        // Zeichne Spielsteine
        for (int row = 0; row < size; row++)
            for (int col = 0; col < size; col++) {
                int piece = connect6Board.getStone(row, col);
                if (piece != Connect6Board.NONE) {
                    Color c = (piece == Connect6Board.BLACK) ? Color.BLACK : Color.WHITE;
                    g2.setColor(c);
                    double xCenter = xLeft + col * squareWidth;
                    double yCenter = yTop + row * squareWidth;
                    Ellipse2D.Double circle
                            = new Ellipse2D.Double(xCenter - StoneDiameter / 2,
                            yCenter - StoneDiameter / 2,
                            StoneDiameter,
                            StoneDiameter);
                    g2.fill(circle);
                    g2.setColor(Color.black);
                    g2.draw(circle);
                }
            }
    }

    public class Connect6Listener extends MouseAdapter {
        public void mouseReleased(MouseEvent e) {
            double panelWidth = getWidth(); // Breite des Panels
            double panelHeight = getHeight(); // HÃ¶he des Panels
            double boardWidth = Math.min(panelWidth, panelHeight) - 2; // SpielbrettgrÃ¶ÃŸe
            double squareWidth = boardWidth / size; // GrÃ¶ÃŸe der Spielsteine
            double xLeft = (panelWidth - boardWidth) / 2; // Setienabstand links ab dem das Spielfeld beginnt
            double yTop = (panelHeight - boardWidth) / 2; // Seitenabstand oben ab dem das Spielfeld beginnt
            int col = (int) Math.round((e.getX() - xLeft) / squareWidth - 0.5); // Reihe des Spielfeldes in der die Maus losgelassen wurde
            int row = (int) Math.round((e.getY() - yTop) / squareWidth - 0.5); // Spalte des Spielfeldes in der die Maus losgelassen wurde

            if (connect6Board.playPiece(row, col)) {
                repaint();
                if (connect6Board.checkDraw()) {
                    JOptionPane.showMessageDialog(null, "Draw.");
                    resetPanel(size);
                }
                if (connect6Board.checkWinner(row, col)){
                    if (connect6Board.getPlayer() == Connect6Board.BLACK) {
                        JOptionPane.showMessageDialog(null, "Black wins!");
                    } else {
                        JOptionPane.showMessageDialog(null,"White wins!");
                    }
                    resetPanel(size);
                }
                connect6Board.changePlayer();
            }
        }
    }

}

