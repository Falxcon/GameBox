package core;
import javax.swing.*;

public abstract class GameControl {

	View gameView;

	public abstract JPanel getPanel();

	public abstract int getUpdateInterval();

}