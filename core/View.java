package core;
import javax.swing.*;

public class View extends JInternalFrame{

	private Frame frame;
	private GameModel myGame;

	/**
	 * 
	 * @param game
	 */
	public View(GameModel game, Frame mainFrame) {
		myGame = game;
		frame = mainFrame;

		getContentPane().add(game.getPanel());
		setTitle(game.getGameTitle());

		setVisible(true);
	}

}