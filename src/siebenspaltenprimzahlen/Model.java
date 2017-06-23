package siebenspaltenprimzahlen;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomwi on 23.06.2017.
 */
public class Model {
    List<Primer> primePipes;
    //JTextArea[] textAreas;
    View view;

    Model(View view){
        primePipes = new ArrayList<>();
        this.view = view;
        //textAreas = view.textArea;
    }

    public void start(){
        for(int i = 0; i < 7; i++){
            primePipes.add(new Primer(2, i, view));
        }

        for(int i = 3; i <= 6000; i++){
            for(Primer p : primePipes){
                p.send(i);
            }
        }

        for(Primer p : primePipes){
            p.send(0);
        }
    }
}
