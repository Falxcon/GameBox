package siebenspaltenprimzahlen;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Seb on 22.06.2017.
 */
public class Jiframe extends JInternalFrame {

    JTextArea textArea;
    JScrollPane jScrollPane;
    SiebenSpaltenPrimzahlen s;
    ArrayList<Integer> prime = new ArrayList<>();


    public Jiframe(){
        super("SiebenSpaltenPrimzahlen",true,true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(500,300);
        textArea = new JTextArea(50,50);
        jScrollPane= new JScrollPane(textArea);
        add(jScrollPane);
        s=new SiebenSpaltenPrimzahlen(2,"");
        prime=s.main();


        try {
            Thread.sleep(10000);
            prim(prime);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        }







        public void prim(ArrayList a) {

            for(int x=0;x<a.size();x++){
                textArea.append(a.get(x).toString()+"\n");
            }


        }





    }

