package siebenspaltenprimzahlen;

/**
 * Created by Seb on 22.06.2017.
 */

import java.util.ArrayList;
import java.util.TimerTask;

// SiebenSpaltenPrimzahlen der "Pipe" zur Primzahlenaussiebung
public class SiebenSpaltenPrimzahlen extends Thread {

    private int p; // die Primzahl dieses Primers
    private SiebenSpaltenPrimzahlen next; // der nächste Primer in der "Pipe"
    private String fill;
    private int buffer = -1; // Puffer zum Senden & Empfangen
    static ArrayList<Integer> prim = new ArrayList<>();
    public SiebenSpaltenPrimzahlen(int prime, String fill) { // Konstuktor
        super ("SiebenSpaltenPrimzahlen-" + prime); // Name eintragen
        p = prime; // Primzahl eintragen
        this.fill = fill;
        this.start(); // Thread sofort starten
    }

    public static ArrayList<Integer> main () {
        ArrayList <SiebenSpaltenPrimzahlen> AllPrimer = new ArrayList();
        String string = "               ";
        String leer = "";
        for (int i = 0; i < 7; i++) { // erstelle 7 Primer
            AllPrimer.add(new SiebenSpaltenPrimzahlen(2,leer));
            leer = leer + string;
        }

        for (int i=3; i <= 6000; i++) {
            for (SiebenSpaltenPrimzahlen p : AllPrimer) { // starte alle Primer
                p.send(i);
            }
        }
        for (SiebenSpaltenPrimzahlen p : AllPrimer) { // beende alle Primer
            p.send(0);
        }

        try {
            Thread.sleep(10000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        return prim;


    }

    public void run() { // Die Arbeitsmethode des Primers
// ... ist nicht synchronisiert !


        while (true) { // Endlos-Schleife
            int n = receive(); // Lese-Versuch
            if (n == 0) { // wenn n=0: Ende
                if (next != null) next.send(n);// auch von next
                prim.add(p);
                System.out.println(fill +p);
                break; // Ende while loop
            }
            if (n%p != 0) { // vielleicht prim
                if (next != null) next.send(n);// weiter testen
                else next = new SiebenSpaltenPrimzahlen(n, fill);  // Primzahl!
            } // sonst: n nicht prim
        }
    }

    // wenn < 0: leer
    synchronized void send(int i) { // Sperre erlangen
        try {
            while (buffer >= 0) wait();// warten bis Puffer frei
            buffer = i; // Puffer füllen
            notify(); // Empfänger benachrichtigen
        } catch (InterruptedException e) {}
    }

    synchronized int receive() { // Sperre erlangen
        int result = 0;
        try {
            while ((result=buffer)<0) wait(); // warten bis Puffer
// voll
            buffer = -1; // Puffer leeren
            notify(); // Sender
// benachrichtigen
        } catch (InterruptedException e) {}
        return result;
    }
}

