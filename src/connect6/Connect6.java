package connect6;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Connect6 extends JInternalFrame {

    Connect6MenuBar menuBar;
    Connect6Panel panel;

    public Connect6(int size) {
        super("Connect6", false, true);
        setSize(600, 600);
        setTitle("Connect6");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        menuBar = new Connect6MenuBar();
        panel = new Connect6Panel(size);

        add(panel);
        setJMenuBar(menuBar);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Connect6(19);
    }

    /**
     *
     */
    public class Connect6MenuBar extends JMenuBar implements ActionListener {

        JMenuItem newGame, newWindow, close;

        public Connect6MenuBar() {
            newGame = new JMenuItem("New Game");
            newGame.addActionListener(this);
            add(newGame);

            newWindow = new JMenuItem("New Window");
            newWindow.addActionListener(this);
            add(newWindow);

            close = new JMenuItem("Close Program");
            close.addActionListener(this);
            add(close);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == newGame) {
                new Connect6Slider();
            } else if (e.getSource() == newWindow) {
                new Connect6(19);
            } else if (e.getSource() == close) {
                System.exit(0);
            }
        }
    }

    /**
     *
     */
    public class Connect6Slider extends JFrame implements ActionListener, ChangeListener {

        JButton ok, cancel;
        JSlider slider;

        public Connect6Slider() {
            setLayout(new FlowLayout());
            ok = new JButton("OK");
            ok.addActionListener(this);
            cancel = new JButton("CANCEL");
            cancel.addActionListener(this);
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
            setLocationRelativeTo(null);
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == ok) {
                panel.resetPanel(slider.getValue());
                dispose();
            } else if (e.getSource() == cancel) {
                dispose();
            }
        }

        @Override
        public void stateChanged(ChangeEvent e) {
        }
    }
}
