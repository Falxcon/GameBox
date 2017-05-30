package gameoflife.userinterface;

import gameoflife.model.GOLSimulation;

import javax.swing.*;

public class GOLMain extends JInternalFrame{

    private GOLMenuBar menuBar;
    private GOLGridPanel gridPanel;

    private GOLSimulation simulation;

    public GOLMain(int width, int height) {
        super("Game of Life", false, true);
        simulation = new GOLSimulation(width,height);
        gridPanel = new GOLGridPanel(simulation);
        simulation.addObserver(gridPanel);

        menuBar = new GOLMenuBar(simulation, gridPanel, this);
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
