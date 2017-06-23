package siebenspaltenprimzahlen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;


/*
* @author Sebastian Glück
* */
public class View extends JInternalFrame{
    JTextArea[] textArea;
    JScrollPane scrollPane;
    AdjustmentListener adjustmentListener;

    public View(){
        super("SiebenSpaltenPrimzahlen",true,true);//Konstruktor des JInternalFrame
        init();
    }

    public void init(){
        setSize(400, 700);
        setVisible(true);

        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout(1, 7));
        textArea = new JTextArea[7];
        for(int i = 0; i < 7; i++){
            JTextArea area = new JTextArea();
            area.setBackground(Color.BLACK);
            area.setForeground(Color.GREEN);
            textArea[i] = area;
            pane.add(area);
        }
        scrollPane = new JScrollPane(pane);
        adjustmentListener = new AdjustmentListener() {//Funktion zum automatischen ans Ende scrollen
            public void adjustmentValueChanged(AdjustmentEvent e) {
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
            }
        };
        scrollPane.getVerticalScrollBar().addAdjustmentListener(adjustmentListener);
        getContentPane().add(scrollPane);
        setVisible(true);

        Model model = new Model(this);
        model.start();
    }

    public void finished(){
        scrollPane.getVerticalScrollBar().removeAdjustmentListener(adjustmentListener);
    }//Methode die das ans Ende scrollen beendet
}
