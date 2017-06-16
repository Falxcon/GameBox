import gameoflife.userinterface.IFrameMain;

import javax.swing.*;

public class Frame extends JFrame{

	private JDesktopPane desktopPane;

	private JMenu menuAdd;

	private JMenuItem menuItemGOL;
	
	Frame (){
	    setJMenuBar(initMenuBar());

		setTitle("Game Box");
		setSize(1700, 900);
		desktopPane = new JDesktopPane();
		desktopPane.setDesktopManager(new DefaultDesktopManager());
		setContentPane(desktopPane);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}

    private JMenuBar initMenuBar() {
	    JMenuBar menuBar = new JMenuBar();

	    menuAdd = new JMenu("Add");
        menuBar.add(menuAdd);

	    menuItemGOL = new JMenuItem("gameoflife");
	    menuItemGOL.addActionListener(l -> {
	        desktopPane.add(new IFrameMain(25, 25, desktopPane));
        });
	    menuAdd.add(menuItemGOL);

		JMenuItem menuItemSokoban = new JMenuItem("sokoban");
		menuItemSokoban.addActionListener(l -> {
			desktopPane.add(new sokoban.View());
		});
		menuAdd.add(menuItemSokoban);

	    return menuBar;
    }
}