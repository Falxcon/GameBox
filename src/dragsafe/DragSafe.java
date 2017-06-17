package dragsafe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class DragSafe extends JInternalFrame implements MouseMotionListener {

    private static final int PASSWORD[] = {0, 7, 0, 4, 7, 1, 0, 2};
    private int countPW; // PasswortZähler für richtige Eingabe
    private JButton[] jButtons = new JButton[10];
    private JButton[] index = new JButton[10];
    private boolean rotation = true; // true = uhrzeigersinn, false = gegen den uhrzeigersinn;
    private long rotationSpeed; // Zeit in ms bis zur nächsten Rotation
    private boolean dragStop; // Verhindert die mehrfache abtastung von mouseDragged

    private Thread thread;

    public DragSafe(long rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
        dragStop = false;
        countPW = 0;

        // Erstellt Buttons von 0 - 9
        for (int i = 0; i < jButtons.length; i++) {
            jButtons[i] = new JButton("" + i);
        }
        // Erstellt einen Index für die Buttonanordung
        index[0] = jButtons[0];
        index[1] = jButtons[1];
        index[2] = jButtons[2];
        index[4] = jButtons[3];
        index[6] = jButtons[4];
        index[9] = jButtons[5];
        index[8] = jButtons[6];
        index[7] = jButtons[7];
        index[5] = jButtons[8];
        index[3] = jButtons[9];


        // Erstellt thread zur Rotation der Buttons
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (thread.isAlive()) {
                    for (int i = 0; i <= 13; i++) {
                        if (Thread.interrupted())
                            return;
                        if (rotation) {
                            rotateR();
                        } else {
                            rotateL();
                        }
                        try {
                            Thread.sleep(rotationSpeed);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    changeRotation();
                }
            }
        });

        setSize(500, 500);
        setTitle("DragSafe");
        setLayout(new GridLayout(4, 3));
        for (int z = 0, i = 0; i < 12; i++) {
            if (i == 4 || i == 7) {
                JPanel jPanel = new JPanel();
                add(jPanel);
            } else {
                jButtons[z].addMouseMotionListener(this);
                add(index[z]);
                z++;
            }
        }
        // Programm wird geschlossen beim schließen des Fensters
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
        setVisible(true); // Fenster wird sichtbar
        thread.start();
    }

    public void mouseMoved(MouseEvent me) {
        dragStop = false; // mouseDragged kann wieder abtasten
    }

    public void mouseDragged(MouseEvent md) {
        if (!dragStop) {
            if (((AbstractButton) md.getSource()).getActionCommand().equals(Integer.toString(PASSWORD[countPW]))) {
                countPW++; // Erhöht den Zähler bei einer richtigen Eingabe
                // Fenster wird geschlossen, wenn das passwort vollständig eingegeben wurde
                if (countPW == PASSWORD.length) {
                    thread.interrupt();
                    dispose();
                }
                changeColorBtn(Color.GREEN); // Hintergrundfarbe der Buttons wird auf grün geändert
            } else {
                countPW = 0; // Setzt bei falscher Eingabe Zähler zurück
                changeColorBtn(Color.RED); // Hintergrundfarbe der Buttons wird auf rot geändert
                new DragSafe((long)(rotationSpeed * 0.5)); // Erstellt ei neues Fenster mit erhöter Rotationsgeschwindigkeit
            }
            dragStop = true; // verhindert die mehrfache abtastung (Event wird nur einmal ausgeführt)
        }
    }

    /**
     * Wechslet die Rotationsrichtung
     */
    private void changeRotation() {
        if (rotation) {
            rotation = false;
        } else {
            rotation = true;
        }
    }

    /**
     * Verändert die Farbe der Buttons
     * @param c Farbe der Buttons
     */
    private void changeColorBtn(Color c) {
        for (JButton jB : jButtons) {
            jB.setBackground(c);
        }
    }

    /**
     * Buttons rotieren gegen den uhrzeigersinn
     */
    private void rotateL() {
        for (JButton jB : jButtons) {
            if (jB.getActionCommand().equals("9")) {
                jB.setText("" + 0);
            } else {
                jB.setText((Integer.parseInt(jB.getActionCommand()) + 1) + "");
            }
        }
    }

    /**
     * Buttons rotieren im uhrzeigersinn
     */
    private void rotateR() {
        for (JButton jB : jButtons) {
            if (jB.getActionCommand().equals("0")) {
                jB.setText("" + 9);
            } else {
                jB.setText((Integer.parseInt(jB.getActionCommand()) - 1) + "");
            }
        }
    }
}
