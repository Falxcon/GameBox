package gameoflife.userinterface;

import gameoflife.model.Simulation;

import javax.swing.*;

public class IFrameMain extends IFrameBasic {

    private JDesktopPane jDesktopPane;
    private MenuBar menuBar;
    private GridPanelMain gridPanelMain;

    private Simulation simulation;

    public IFrameMain(int width, int height, JDesktopPane jdp) {
        this.jDesktopPane = jdp;

        this.simulation = new Simulation(width, height);
        this.gridPanelMain = new GridPanelMain(simulation);
        this.simulation.addObserver(gridPanelMain);

        initComponents();
    }

    public IFrameMain(Simulation s, JDesktopPane jdp) {
        this.jDesktopPane = jdp;

        this.simulation = new Simulation(s);
        this.gridPanelMain = new GridPanelMain(simulation);
        this.simulation.addObserver(gridPanelMain);

        initComponents();
    }

    private void initComponents() {
        menuBar = new MenuBar();
        menuBar.addMenuMod(simulation, gridPanelMain);
        menuBar.addMenuSpeed(simulation);
        menuBar.addMenuForm(simulation);
        menuBar.addMenuWindow(simulation, jDesktopPane);
        menuBar.addMenuCell(gridPanelMain, this);
        menuBar.addMenuRotate(gridPanelMain, this);

        add(gridPanelMain);
        setJMenuBar(menuBar);
        setWindowSize(gridPanelMain);
        setVisible(true);
    }
}
