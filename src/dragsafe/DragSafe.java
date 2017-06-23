package dragsafe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
/*
* @author Tom Wittal, Sebastian Glück, Valentin Lutz
* */



public class DragSafe extends JInternalFrame implements MouseMotionListener {

    private static final int PASSWORD[] = {0, 7, 0, 4, 7, 1, 0, 2};
    private int countPW; // PasswortZähler für richtige Eingabe
    private JButton[] jButtons = new JButton[10];
    private JButton[] index = new JButton[10];
    private boolean rotation = true; // true = uhrzeigersinn, false = gegen den uhrzeigersinn;
    private long rotationSpeed; // Zeit in ms bis zur nächsten Rotation
    private boolean dragStop; // Verhindert die mehrfache abtastung von mouseDragged

    private JDesktopPane jDesktopPane;
    private Thread thread;

    public DragSafe(long rotationSpeed, JDesktopPane jdp) {
        this.rotationSpeed = rotationSpeed;
        jDesktopPane = jdp;
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
        Runnable task = () -> {
            while (Thread.currentThread().isAlive()) {
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
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // Thread zerstört sich selbst
                    return;
                }
            }
        };
        thread = new Thread(task);

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
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        thread.start();
    }

    public void mouseMoved(MouseEvent me) {
        // mouseDragged kann wieder abtasten
        dragStop = false;
    }

    public void mouseDragged(MouseEvent md) {
        if (!dragStop) {
            if (((AbstractButton) md.getSource()).getActionCommand().equals(Integer.toString(PASSWORD[countPW]))) {
                // Erhöht den Zähler bei einer richtigen Eingabe
                countPW++;
                // Fenster wird geschlossen, wenn das passwort vollständig eingegeben wurde
                if (countPW == PASSWORD.length) {
                    thread.interrupt();
                    dispose();
                }
                // Hintergrundfarbe der Buttons wird auf grün geändert
                changeColorBtn(Color.GREEN);
            } else {
                // Setzt bei falscher Eingabe Zähler zurück
                countPW = 0;
                // Hintergrundfarbe der Buttons wird auf rot geändert
                changeColorBtn(Color.RED);
                // Erstellt ei neues Fenster mit erhöter Rotationsgeschwindigkeit
                jDesktopPane.add(new DragSafe((long)(rotationSpeed * 0.5), jDesktopPane));
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
