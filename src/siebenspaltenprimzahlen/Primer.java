package siebenspaltenprimzahlen;

/**
 * Created by tomwi on 23.06.2017.
 */
import javax.swing.*;
import java.awt.*;

public class Primer extends Thread{
    private int r, p, buffer = -1;
    private Primer next;
    private JTextArea[] textArea;
    private View view;


    Primer (int prime, int row, View view){
        super ("" + prime);
        p = prime;
        r = row;
        this.view = view;
        textArea = view.textArea;
        this.start();
    }

    public void run(){
        String line = Integer.toString(p);
        for(int i = 0; i < 7; i++){
            if(i == r) textArea[i].append(line + "\n");
            else textArea[i].append("\n");
        }

        while(true){
            int n = receive();
            if (n == 0) {
                if (next != null) next.send(n);
                else {
                    view.finished();
                }
                break;
            }
            if (n%p != 0) {
                if (next != null) next.send(n);
                else next = new Primer(n, r, view);
            }
        }
    }

    synchronized void send(int i) {
        try {
            while (buffer >= 0) wait();
            buffer = i;
            notify();
        } catch (InterruptedException e) {}
    }

    synchronized int receive() {
        int result = 0;
        try {
            while ((result = buffer)<0) wait();
            buffer = -1;
            notify();
        } catch (InterruptedException e) {}
        return result;
    }

}
