package mvcexample;

/*
* @author Tom Wittal, Sebastian Glück, Valentin Lutz
* */
import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

class GraphQView extends JPanel implements Observer {
    Qpolynom myPolynom;
    int points = 2000;
    int skalierung = 10;

    GraphQView(Qpolynom q){
        myPolynom = q;
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int maxWidth = getWidth();
        int maxHeight = getHeight();
        double hstep = (double) maxWidth / (double) points;
        int x1 = maxWidth/2;
        int x2 = maxWidth/2;
        int y1 = 0;
        int y2 = maxHeight;
        g.setColor(Color.black);
        g.drawLine(x1,y1,x2,y2);

        x1 = 0;
        x2 = maxWidth;
        y1 = maxHeight/2;
        y2 = maxHeight/2;
        g.drawLine(x1,y1,x2,y2);

        g.setColor(Color.RED);
        int[] pts =  new int[points];
        for(int i = 0; i <points; i++){
            double x = i*0.01 -skalierung;
            pts[i]= (int)(maxHeight/2 -(myPolynom.getConstant()+ x*myPolynom.getLinear() + Math.pow(x,2)* myPolynom.getQuadratic() + Math.pow(x,3)*myPolynom.getKubik()));
        }
        for( int i =1; i < points; i++){
            x1 = (int) ((i - 1) * hstep);
            x2 = (int) (i * hstep);
            y1 = pts[i - 1];
            y2 = pts[i]; //
            g.drawLine(x1, y1, x2, y2);
        }
    } // end paintComponent


    public void update (Observable o, Object arg){	// fuer Observer
        if (o==myPolynom) repaint ();			// neu darstellen
    }


} // end GraphQView
