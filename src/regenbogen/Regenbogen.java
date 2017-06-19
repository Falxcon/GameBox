package regenbogen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Regenbogen extends basicFrame{

    Button bNewWindow = new Button("Neues Fenster");

    public Regenbogen() {
        setLayout(new FlowLayout());
        bNewWindow.addActionListener(this::actionPerformed);
        add(bNewWindow);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(100, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.bNewWindow) {
            newBasicFrame();
        }
    }

    public void newBasicFrame() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                basicFrame bf = new basicFrame();
                bf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                bf.setSize(400,400);
                bf.setVisible(true);
                while (true) {
                    bf.changeBackground();
                }
            }
        });
        thread.start();
    }
}

class basicFrame extends JInternalFrame {
    // Regenbogenfarben
    static String colors[] = {"Black", "Blue", "Cyan", "Gray", "Green", "Magenta", "Orange", "Pink", "Red", "White", "Yellow"};

    int countColor = 0;

    public void changeBackground() {
        try {
            // Setzt die Hintergrundfarbe des Fensters
            setBackground((Color) Color.class.getField(colors[countColor].toLowerCase()).get(null));
            // Wechsle zur nächsten Farbe
            countColor = (countColor + 1) % colors.length;
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
