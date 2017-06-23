package drehsafe;

/*
* @author Sebastian Glück
* */
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;
import javax.swing.*;

public class View extends JInternalFrame implements ActionListener {

    int direction, 		// +1 -> im Uhrzeigersinn -1 -> gegen Uhrzeigersinn
            step, 			//merkt poition in der Kombinaion
            colorStatus; 	// 0 = white, 1 = green, 2 = red
    Container pane;
    int[] combination = new int[]{3, 0, 0, 3, 2, 0, 1, 7};
    int[] nums = new int[]{1, 0, 9, 2, -2, 8, 3, -2, 7, 4, 5, 6};		//reihenfolge der Zahlen auf den Buttons
    int[] turnRight = new int[]{3, 0, 1, 6, 4, 2, 9, 7, 5, 10, 11, 8};	//index zum Vertauschen
    int[] turnLeft = new int[]{1, 2, 5, 0, 4, 8, 3, 7, 11, 6, 9, 10};

    public View(){
        super("Dreh Safe", false, true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);

        pane = getContentPane();
        pane.setLayout(new GridLayout(4, 3));

        direction = 1;
        step = 0;
        colorStatus = 0;

        for(int i = 0; i < 12; i++){
            JButton btn = new JButton(Integer.toString(nums[i]));
            btn.addActionListener(this);
            btn.setActionCommand(Integer.toString(nums[i]));
            btn.setBackground(Color.WHITE);

            pane.add(btn);
            if(i == 4 || i == 7) btn.setVisible(false); // mittlere Buttons zu Platzhalter machen
        }

        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                rotate();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void refreshBtns(){
        Component[] btns = pane.getComponents(); //alle components holen
        for(int i = 0; i < 12; i++){
            JButton btn = (JButton)btns[i]; //Component zu JButton casten damit text und action command gesetzt werden kann
            btn.setText(Integer.toString(nums[i]));
            btn.setActionCommand(Integer.toString(nums[i]));
            if(colorStatus == 1) btn.setBackground(Color.GREEN);
            else if(colorStatus == 2) btn.setBackground(Color.RED);
        }

    }

    private void rotate(){
        int[] newNums = new int[12];
        int[] index = direction == 1 ? turnRight : turnLeft;

        for(int i = 0; i < newNums.length; i++){
            newNums[i] = nums[index[i]]; //neue Zahlenreihenfolge festlegen
        }

        nums = newNums;
        refreshBtns();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int number = Integer.parseInt(e.getActionCommand());
        if(number == combination[step]){
            for(Component c : pane.getComponents()){
                c.setBackground(Color.GREEN);
            }
            colorStatus = 1;
            step++;
            if(step == combination.length) this.dispose(); //Programm beenden
        } else {
            for(Component c : pane.getComponents()){
                c.setBackground(Color.RED);
            }
            colorStatus = 2;
            direction = direction == 1 ? -1 : 1;	//Drehrichtung wechseln
            step = 0;
        }
    }

}
