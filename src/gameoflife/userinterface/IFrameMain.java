package gameoflife.userinterface;

import gameoflife.model.Simulation;

import javax.swing.*;

public class IFrameMain extends IFrameBasic {

    private JDesktopPane jDesktopPane;
    private MenuBar menuBar;
    private GridPanelMain gridPanelMainView;

    private Simulation simulation;

    public IFrameMain(int width, int height, JDesktopPane jDesktopPane) {
        this.jDesktopPane = jDesktopPane;

        simulation = new Simulation(width,height);
        gridPanelMainView = new GridPanelMain(simulation);
        simulation.addObserver(gridPanelMainView);

        menuBar = new MenuBar();
        menuBar.addMenuMod(simulation, gridPanelMainView);
        menuBar.addMenuSpeed(simulation);
        menuBar.addMenuForm(simulation);
        menuBar.addMenuWindow(simulation, jDesktopPane);
        menuBar.addMenuCell(gridPanelMainView, this);

        add(gridPanelMainView);
        setJMenuBar(menuBar);
        setWindowSize(gridPanelMainView);
        setVisible(true);
    }
}
