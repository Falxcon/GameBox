package core;

import javax.swing.*;

public class Frame extends JFrame{

	private JDesktopPane desktopPane;
	
	Frame (){
		setTitle("Game Box");
		setSize(1700, 900);
		desktopPane = new JDesktopPane();
		desktopPane.setDesktopManager(new DefaultDesktopManager());
		setContentPane(desktopPane);
		setVisible(true);



	}

}