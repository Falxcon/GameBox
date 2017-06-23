package mvcexample;

/*
* @author Tom Wittal, Sebastian Glück, Valentin Lutz
* */
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.* ;
import java.awt.*;
import java.awt.event.*;
import java.util.*;				// fuer Observer und Observable


class TextQView extends JPanel implements Observer {
    JTextField
            a = new JTextField (10),
            b = new JTextField (10),
            c = new JTextField (10),
            d = new JTextField (10);
    JLabel
            al = new JLabel ("Konstante",JLabel.RIGHT),
            bl = new JLabel ("Linearer Koeffizient",JLabel.RIGHT),
            cl = new JLabel ("Quadratischer Koeffizient",JLabel.RIGHT),
            dl = new JLabel ("Kubischer Koeffizient",JLabel.RIGHT);

    Qpolynom myPolynom;
    TextQView (Qpolynom q) {
        myPolynom = q;
        setLayout (new GridLayout(4,2,5,5));
        add (al); add (a);
        add (bl); add (b);
        add (cl); add (c);
        add (dl); add (d);
        a.setEditable (false);
        b.setEditable (false);
        c.setEditable (false);
        d.setEditable (false);
    }


    public void update (Observable o, Object arg){
        if (o==myPolynom) repaint ();

    }
    public void paintComponent (Graphics g) {
        super.paintComponent (g);
        a.setText (""+myPolynom.getConstant());
        b.setText (""+myPolynom.getLinear());
        c.setText (""+myPolynom.getQuadratic());
        d.setText (""+myPolynom.getKubik());
    }
}
