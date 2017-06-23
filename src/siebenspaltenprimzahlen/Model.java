package siebenspaltenprimzahlen;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/*
* @author Sebastian Glück
* */

public class Model {
    List<Primer> primePipes;
    View view;

    Model(View view){
        primePipes = new ArrayList<>();
        this.view = view;

    }

    public void start(){
        for(int i = 0; i < 7; i++){//Sieben Spalten erstellen
            primePipes.add(new Primer(2, i, view));
        }

        for(int i = 3; i <= 6000; i++){//Senden der Zahlen von 3 - 6000
            for(Primer p : primePipes){
                p.send(i);
            }
        }

        for(Primer p : primePipes){//Abbruch der Primzahlenbestimmung
            p.send(0);
        }
    }
}
