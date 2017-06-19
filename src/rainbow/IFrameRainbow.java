package rainbow;

import javax.swing.*;
import java.awt.*;

public class IFrameRainbow extends JInternalFrame{

    // Regenbogenfarben
    static final String colors[] = {"Black", "Blue", "Cyan", "Gray", "Green", "Magenta", "Orange", "Pink", "Red",
            "White", "Yellow"};
    int countColor = 0;

    public IFrameRainbow() {
        super("Rainbow", true, true);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);
        startThread();
    }

    public void startThread() {
        Runnable task = () -> {
            while (Thread.currentThread().isAlive()) {
                changeBackground();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // Thread zerstört sich selbst
                    return;
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

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
