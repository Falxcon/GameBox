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

		JInternalFrame aView = new View(new connectsix.Connect6(), this);
		aView.setLocation(100, 100);
		aView.setSize(400, 400);
		desktopPane.add(aView);
	}

}