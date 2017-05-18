package connectsix;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by tomwi on 18.05.2017.
 */
public class StartPanel extends JPanel {
    StartPanel(Connect6 game){
        setLayout(new GridLayout(2, 1));

        JSlider slider = new JSlider(SwingConstants.HORIZONTAL, 6, 20, 10);
        slider.setBorder(new TitledBorder("Feldgröße"));
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        this.add(slider);

        JButton startBtn = new JButton("Neues Spiel");
        startBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e){
                game.start(slider.getValue());
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {}

            @Override
            public void mouseExited(MouseEvent arg0) {}

            @Override
            public void mousePressed(MouseEvent arg0) {}

            @Override
            public void mouseReleased(MouseEvent arg0) {}
        });
        this.add(startBtn);
    }
}
