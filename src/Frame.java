import drehsafe.IFrameMain;

import javax.swing.*;

public class Frame extends JFrame{

	private JDesktopPane desktopPane;

	private JMenu menuAdd;

	private JMenuItem menuItemGOL, menuItemSokoban, menuItemDrehSafe, menuItemDragSafe, menuItemConnect6;
	
	Frame (){
	    setJMenuBar(initMenuBar());

		setTitle("Game Box");
		setSize(1700, 900);
		desktopPane = new JDesktopPane();
		desktopPane.setDesktopManager(new DefaultDesktopManager());
		setContentPane(desktopPane);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

    private JMenuBar initMenuBar() {
	    JMenuBar menuBar = new JMenuBar();

	    menuAdd = new JMenu("Add");
        menuBar.add(menuAdd);
		// gameoflife
	    menuItemGOL = new JMenuItem("gameoflife");
	    menuItemGOL.addActionListener(l -> {
	        desktopPane.add(new gameoflife.userinterface.IFrameMain(25, 20, desktopPane));
        });
	    menuAdd.add(menuItemGOL);
		// sokoban
		menuItemSokoban = new JMenuItem("sokoban");
		menuItemSokoban.addActionListener(l -> {
			desktopPane.add(new sokoban.View());
		});
		menuAdd.add(menuItemSokoban);
		// drehsafe
		menuItemDrehSafe = new JMenuItem("drehsafe");
		menuItemDrehSafe.addActionListener(l -> {
			desktopPane.add(new IFrameMain());
		});
		menuAdd.add(menuItemDrehSafe);
		// dragsafe
		menuItemDragSafe = new JMenuItem("dragsafe");
		menuItemDragSafe.addActionListener(l -> {
			desktopPane.add(new dragsafe.DragSafe(1000));
		});
		menuAdd.add(menuItemDragSafe);
		// connect6
		menuItemConnect6 = new JMenuItem("connect6");
		menuItemConnect6.addActionListener(l -> {
			desktopPane.add(new connect6.Connect6(19));
		});
		menuAdd.add(menuItemConnect6);

	    return menuBar;
    }
}