package elffarben;

import javax.swing.*;
import java.awt.*;

public class ElfFarben extends JInternalFrame {
    // buttonlabel
    static String colors[] = {"Black", "Blue", "Cyan", "Gray", "Green", "Magenta", "Orange", "Pink", "Red", "White", "Yellow"};

    public ElfFarben() {
        super("ElfFarben", false, true);

        setLayout(new FlowLayout());
        // Erstellt buttons
        for (int i = 0; i < colors.length; i++) {
            Button btn = new Button(colors[i]);
            btn.addActionListener(l -> {
                try {
                    // Setzt die Hintergrundfarbe des Fensters auf den Namen des buttonlabels
                    setBackground((Color) Color.class.getField(btn.getActionCommand().toLowerCase()).get(null));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            add(btn);
        }

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setVisible(true);
    }
}
