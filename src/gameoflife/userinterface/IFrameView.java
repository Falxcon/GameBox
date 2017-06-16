package gameoflife.userinterface;

import gameoflife.model.Simulation;

public class IFrameView extends IFrameBasic{

    private MenuBar menuBar;
    private GridPanelBasic gridPanelBasic;

    private Simulation simulation;

    public IFrameView(Simulation simulation) {
        this.simulation = simulation;
        gridPanelBasic = new GridPanelBasic(simulation);
        simulation.addObserver(gridPanelBasic);

        menuBar = new MenuBar();
        menuBar.addMenuCell(gridPanelBasic, this);

        add(gridPanelBasic);
        setJMenuBar(menuBar);
        setWindowSize(gridPanelBasic);
        setVisible(true);
    }
}
