package mvcexample;

/*
* @author Tom Wittal, Sebastian Glück, Valentin Lutz
* */

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class MVCexample extends JInternalFrame {        // Das GUI-Programm
    JSlider sa, sb, sc, sd;                // Controller

    public MVCexample(){
        super("MVC Example", false, true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400, 600);
        init();
        setVisible(true);
    }

    public void init() {
        Container cp = getContentPane();
        cp.setLayout(new GridLayout(6, 1, 10, 10));

        final Qpolynom p = new Qpolynom(1, 2, 3, 4);

        sa = new JSlider(SwingConstants.HORIZONTAL, -10, 10, 1);
        sb = new JSlider(SwingConstants.HORIZONTAL, -10, 10, 2);
        sc = new JSlider(SwingConstants.HORIZONTAL, -10, 10, 3);
        sd = new JSlider(SwingConstants.HORIZONTAL, -10, 10, 4);

        sa.setMajorTickSpacing(10);
        sa.setMinorTickSpacing(1);
        sa.setSnapToTicks(true);
        sb.setMajorTickSpacing(10);
        sb.setMinorTickSpacing(1);
        sb.setSnapToTicks(true);
        sc.setMajorTickSpacing(10);
        sc.setMinorTickSpacing(1);
        sc.setSnapToTicks(true);
        sd.setMajorTickSpacing(10);
        sd.setMinorTickSpacing(1);
        sd.setSnapToTicks(true);
        sa.setPaintTicks(true);
        sb.setPaintTicks(true);
        sc.setPaintTicks(true);
        sd.setPaintTicks(true);

        sa.setPaintLabels(true);
        sb.setPaintLabels(true);
        sc.setPaintLabels(true);
        sd.setPaintLabels(true);

        sa.setPreferredSize(new Dimension(400, 70));
        sb.setPreferredSize(new Dimension(400, 70));
        sc.setPreferredSize(new Dimension(400, 70));
        sd.setPreferredSize(new Dimension(400, 70));

        sa.setBorder(new TitledBorder("Konstante"));
        sb.setBorder(new TitledBorder("Linearer Koeffizient"));
        sc.setBorder(new TitledBorder("Quadratischer Koeffizient"));
        sd.setBorder(new TitledBorder("Kubischer Koeffizient"));

        sa.addChangeListener(evt -> {
            JSlider source = (JSlider) evt.getSource();
            if (!source.getValueIsAdjusting()) {
                p.setConstant(source.getValue());
            }
        });

        sb.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                JSlider source = (JSlider) evt.getSource();
                if (!source.getValueIsAdjusting()) {
                    p.setLinear(source.getValue());
                }
            }
        });
        sc.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                JSlider source = (JSlider) evt.getSource();
                if (!source.getValueIsAdjusting()) {
                    p.setQuadratic(source.getValue());
                }
            }
        });
        sd.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                JSlider source = (JSlider) evt.getSource();
                if (!source.getValueIsAdjusting()) {
                    p.setKubik(source.getValue());
                }
            }
        });

        TextQView view1 = new TextQView(p);
        GraphQView view2 = new GraphQView (p);

        p.addObserver(view1);
        p.addObserver(view2);

        cp.add(view1);
        cp.add(view2);

        cp.add(sa);
        cp.add(sb);
        cp.add(sc);
        cp.add(sd);

    }

}
