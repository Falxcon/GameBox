package mvcexample;

/**
 * Created by tomwi on 20.06.2017.
 */
import java.util.Observable;

class Qpolynom extends Observable {			// Beobachtbares
    private int 					// interne Daten
            constant, linear, quadratic, kubik;			// Polynom-Koeffizienten
    public Qpolynom (int a, int b, int c, int d){ 		// Konstuktor
        constant = a;
        linear = b ;
        quadratic = c;
        kubik = d ;
    } // end Konstuktor
    public int getConstant () {			// getter Methode
        return constant;				// konstanter Koeffizient
    }

    public int getLinear () {				// getter Methode
        return linear;				// linearer Koeffizient
    }
    public int getQuadratic () {			// getter Methode
        return quadratic;				// quadratischer Koeffizient
    }
    public int getKubik (){
        return kubik;        //kubischer Koeffizient
    }
    public void setConstant (int n) {			// setter Methode
        constant = n;				// konstanter Koeffizient
        setChanged();
        notifyObservers();
    }
    public void setLinear (int n) {			// setter Methode
        linear = n;				// linearer Koeffizient
        setChanged();
        notifyObservers();
    }
    public void setQuadratic (int n) {			// setter Methode
        quadratic = n;				// quadratischer Koeffizient
        setChanged();
        notifyObservers();
    }
    public void setKubik (int n) {			// setter Methode
        kubik = n;				// quadratischer Koeffizient
        setChanged();
        notifyObservers();
    }
} // end Qpolynom
