package mvcexample;

/**
 * Created by tomwi on 20.06.2017.
 */

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class MVCexample extends JInternalFrame {        // Das GUI-Programm
    JSlider sa, sb, sc, sd;                // Controller

    public MVCexample(){
        setTitle("MVC Example");
        setSize(400, 500);
        init();
        setVisible(true);
    }

    public void init() {
        Container cp = getContentPane();            // Fenster-Container
        cp.setLayout(new GridLayout(6, 1, 10, 10));        // 5x1-Grid, 10-er Abstaende

        final Qpolynom p = new Qpolynom(1, 2, 3, 4);        // das Modell

        sa = new JSlider(SwingConstants.HORIZONTAL, -10, 10, 1);    // Erzeugung
        sb = new JSlider(SwingConstants.HORIZONTAL, -10, 10, 2);    //   der Controller
        sc = new JSlider(SwingConstants.HORIZONTAL, -10, 10, 3);  //
        sd = new JSlider(SwingConstants.HORIZONTAL, -10, 10, 4);

        sa.setMajorTickSpacing(10);            // Parameter
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

        sa.setPaintLabels(true);                // Parameter
        sb.setPaintLabels(true);
        sc.setPaintLabels(true);
        sd.setPaintLabels(true);

        sa.setPreferredSize(new Dimension(400, 70));
        sb.setPreferredSize(new Dimension(400, 70));
        sc.setPreferredSize(new Dimension(400, 70));
        sd.setPreferredSize(new Dimension(400, 70));

        sa.setBorder(new TitledBorder("Konstante"));            // Border fuer
        sb.setBorder(new TitledBorder("Linearer Koeffizient"));    //    Schiebe-
        sc.setBorder(new TitledBorder("Quadratischer Koeffizient"));    //    Regler
        sd.setBorder(new TitledBorder("Kubischer Koeffizient"));

        // Listener, i.Kl.
        sa.addChangeListener(evt -> {
            JSlider source = (JSlider) evt.getSource();
            if (!source.getValueIsAdjusting()) {
                p.setConstant(source.getValue());    // set... benutzen
            }
        });

        sb.addChangeListener(new ChangeListener() {            // Listener, i.Kl.
            public void stateChanged(ChangeEvent evt) {
                JSlider source = (JSlider) evt.getSource();
                if (!source.getValueIsAdjusting()) {
                    p.setLinear(source.getValue());    // set... benutzen
                }
            }
        });
        sc.addChangeListener(new ChangeListener() {            // Listener, i.Kl.
            public void stateChanged(ChangeEvent evt) {
                JSlider source = (JSlider) evt.getSource();
                if (!source.getValueIsAdjusting()) {
                    p.setQuadratic(source.getValue());    // set... benutzen
                }
            }
        });
        sd.addChangeListener(new ChangeListener() {            // Listener, i.Kl.
            public void stateChanged(ChangeEvent evt) {
                JSlider source = (JSlider) evt.getSource();
                if (!source.getValueIsAdjusting()) {
                    p.setKubik(source.getValue());    // set... benutzen
                }
            }
        });

        TextQView view1 = new TextQView(p);            // 1. View
        GraphQView view2 = new GraphQView (p);			// nach Uebung
        //TextQView view2 = new TextQView(p);            // 2. View

        p.addObserver(view1);            // Views als Observer registrieren
        p.addObserver(view2);            // ..

        cp.add(view1);                // Views zum Fenster hinzufuegen
        cp.add(view2);                // ..
        // ...
        // ...
        cp.add(sa);                // Controller hinzufuegen
        cp.add(sb);                // ...
        cp.add(sc);
        cp.add(sd);                // ...

    } // end init

} // end MVCexample
