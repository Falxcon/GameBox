import GameOfLife.UserInterface.GOLMain;

import javax.swing.*;

public class Frame extends JFrame{

	private JDesktopPane desktopPane;
	
	Frame (){
		setTitle("Game Box");
		setSize(1700, 900);
		desktopPane = new JDesktopPane();
		desktopPane.setDesktopManager(new DefaultDesktopManager());
		setContentPane(desktopPane);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
		desktopPane.add(new GOLMain(25, 25));
	}

}