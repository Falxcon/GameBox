package connectsix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by tomwi on 18.05.2017.
 */
public class GUI extends JPanel implements ActionListener {
    Connect6 game;
    JButton[][] btns;
    boolean gameRunning;

    GUI(int size, Connect6 game){
        gameRunning = true;
        this.game = game;
        this.setSize(size*50, size*50);
        this.setLayout(new GridLayout(size, size));
        btns = new JButton[size][size];

        for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
                JButton b = new JButton();
                b.setBackground(Color.WHITE);
                b.setActionCommand(Integer.toString(x) + ":" + Integer.toString(y));
                b.addActionListener((ActionListener) this);
                btns[x][y] = b;
                this.add(b);
            }
        }
    }


    private void refresh(){
        for(int x = 0; x < btns.length; x++){
            for(int y = 0; y < btns.length; y++){
                switch(game.gibFeldStatus(x, y)){
                    case 0:
                        btns[x][y].setBackground(Color.WHITE);
                        break;
                    case 1:
                        btns[x][y].setBackground(Color.BLUE);
                        break;
                    case 2:
                        btns[x][y].setBackground(Color.ORANGE);
                        break;
                }
            }
        }


        if(game.prüfe()){
            game.title = ("Spieler " + game.gibSpieler() + " hat gewonnen");
            gameRunning = false;
        } else if (game.prüfeSpielfeldVoll()) {
            game.title = ("Spielfeld ist voll");
            gameRunning = false;
        } else {
            game.spielerwechsel();
            game.title = ("Spieler " + game.gibSpieler());
        }

    }


    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(gameRunning){
            String coords = arg0.getActionCommand();
            int split = coords.indexOf(":");
            int x = Integer.parseInt(coords.substring(0, split));
            int y = Integer.parseInt(coords.substring(split +1));
            if(game.setzeFeld(x+1, y+1)){
                refresh();
            }
        }


    }

}
