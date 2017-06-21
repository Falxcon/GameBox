package connect6;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Connect6 extends JInternalFrame {

    Connect6MenuBar menuBar;
    Connect6Panel panel;
    JDesktopPane jDesktopPane;

    public Connect6(int size, JDesktopPane jdp) {
        super("Connect6", false, true);
        jDesktopPane = jdp;

        setSize(600, 600);
        setTitle("Connect6");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        menuBar = new Connect6MenuBar();
        panel = new Connect6Panel(size);

        add(panel);
        setJMenuBar(menuBar);
        setVisible(true);
    }

    /**
     *
     */
    public class Connect6MenuBar extends JMenuBar {

        JMenuItem newGame;

        public Connect6MenuBar() {
            newGame = new JMenuItem("New Game");
            newGame.addActionListener(l -> {
                jDesktopPane.add(new Connect6Slider());
            });
            add(newGame);
        }
    }

    /**
     *
     */
    public class Connect6Slider extends JInternalFrame implements ChangeListener {

        JButton ok, cancel;
        JSlider slider;

        public Connect6Slider() {
            setLayout(new FlowLayout());
            ok = new JButton("OK");
            ok.addActionListener(l -> {
                panel.resetPanel(slider.getValue());
                dispose();
            });
            cancel = new JButton("CANCEL");
            cancel.addActionListener(l -> {
                dispose();
            });
            slider = new JSlider(SwingConstants.HORIZONTAL, 6, 59, 19);
            setSize(1000, 200);
            setTitle("Connect6Menu");
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            slider.setMajorTickSpacing(1);
            slider.setPreferredSize(new Dimension(950, 100));
            slider.addChangeListener(this);
            slider.setToolTipText("Größe der Spielfläche einstellen");
            ok.setVisible(true);
            add(slider);
            add(ok);
            add(cancel);
            setVisible(true);
        }

        @Override
        public void stateChanged(ChangeEvent e) {
        }
    }
}
