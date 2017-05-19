package GameOfLife.UserInterface;

import GameOfLife.Model.Simulation;

import javax.swing.*;

public class UIMain extends JInternalFrame{

    private UIMenuBar menuBar;
    private UIGridPanel gridPanel;

    private Simulation simulation;

    public UIMain() {
        super("Game of Life", false, true);
        simulation = new Simulation(25,25);
        gridPanel = new UIGridPanel(simulation);
        simulation.addObserver(gridPanel);

        menuBar = new UIMenuBar(simulation, gridPanel, this);
        menuBar.addMenuMod();
        menuBar.addMenuSpeed();
        menuBar.addMenuForm();
        menuBar.addMenuWindow();
        menuBar.addMenuCell();

        add(gridPanel);
        setJMenuBar(menuBar);
        setWindowSize();
        setVisible(true);
    }

    public void setWindowSize() {
        setSize(gridPanel.getWidth() + getInsets().left + getInsets().right,
                gridPanel.getHeight() + getInsets().top + getInsets().bottom + 46);
    }
}
