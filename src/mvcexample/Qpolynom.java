package mvcexample;

/*
* @author Tom Wittal, Sebastian Glück, Valentin Lutz
* */
import java.util.Observable;

class Qpolynom extends Observable {
    private int
            constant, linear, quadratic, kubik;
    public Qpolynom (int a, int b, int c, int d){
        constant = a;
        linear = b ;
        quadratic = c;
        kubik = d ;
    }
    public int getConstant () {
        return constant;
    }

    public int getLinear () {
        return linear;
    }
    public int getQuadratic () {
        return quadratic;
    }
    public int getKubik (){
        return kubik;
    }
    public void setConstant (int n) {
        constant = n;
        setChanged();
        notifyObservers();
    }
    public void setLinear (int n) {
        linear = n;
        setChanged();
        notifyObservers();
    }
    public void setQuadratic (int n) {
        quadratic = n;
        setChanged();
        notifyObservers();
    }
    public void setKubik (int n) {
        kubik = n;
        setChanged();
        notifyObservers();
    }
}
