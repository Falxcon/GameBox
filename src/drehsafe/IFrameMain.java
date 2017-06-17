package drehsafe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class IFrameMain extends JInternalFrame{

    private static final int PASSWORD[] = {3, 0, 0, 3, 2, 0, 1, 7};
    private int countPW; // PasswortZähler für richtige Eingabe
    private JButton[] jButtons = new JButton[10];
    private JButton[] index = new JButton[10];
    private boolean rotation = true; // true = uhrzeigersinn, false = gegen den uhrzeigersinn;

    private Thread thread;

    public IFrameMain() {
        super("DrehSafe", false, true);
        countPW = 0;

        // Erstellt Buttons von 0 - 9
        for (int i = 0; i < jButtons.length; i++) {
            jButtons[i] = new JButton("" + i);
            jButtons[i].setBackground(Color.GREEN);
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
                    if (rotation) {
                        rotateR();
                    } else {
                        rotateL();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        setSize(500, 500);
        setLayout(new GridLayout(4, 3));
        for (int z = 0, i = 0; i < 12; i++) {
            if (i == 4 || i == 7) {
                JPanel jPanel = new JPanel();
                add(jPanel);
            } else {
                jButtons[z].addActionListener(this::actionPerformed);
                add(index[z]);
                z++;
            }
        }
        // Programm wird geschlossen beim schließen des Fensters
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                thread.stop();
                dispose();
            }
        });
        setVisible(true); // Fenster wird sichtbar
        thread.start();
    }



    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals(Integer.toString(PASSWORD[countPW]))) {
            countPW++; // Erhöht den Zähler bei einer richtigen Eingabe
            // Programm wird geschlossen, wenn das passwort vollständig eingegeben wurde
            if (countPW == PASSWORD.length) {
                thread.stop();
                dispose();
            }
            changeColorBtn(Color.GREEN); // Hintergrundfarbe der Buttons wird auf grün geändert
        } else {
            countPW = 0; // Setzt bei falscher Eingabe Zähler zurück
            changeColorBtn(Color.RED); // Hintergrundfarbe der Buttons wird auf rot geändert
            changeRotation();
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
