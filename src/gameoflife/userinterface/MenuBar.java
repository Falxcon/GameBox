package gameoflife.userinterface;

import gameoflife.model.Simulation;

import javax.swing.*;
/*
* @author Valentin Lutz
* */

public class MenuBar extends JMenuBar {

    private JMenu menuMod, menuSpeed, menuWindow, menuForm, menuCell, menuRotate;
    // menuMod
    private JMenuItem menuItemRun, menuItemPause, menuItemDraw;
    // menuSpeed
    private JMenuItem menuItemFaster, menuItemSlower;
    // menuWindow
    private JMenuItem menuItemClone, menuItemView;
    // menuForm
    private JMenuItem menuItemReset, menuItemRandom, menuItemGlider, menuItemLWWS, menuItemPento;
    // menuCell
    private JMenuItem menuItemIncrease, menuItemDecrease;

    public MenuBar() {
    }

    public void addMenuMod(Simulation simulation, GridPanelMain gridPanelMainView) {
        menuMod = new JMenu("Mod");
        // Run
        menuItemRun = new JMenuItem("Run");
        menuItemRun.addActionListener(l -> {
            simulation.startSimulation();
        });
        menuMod.add(menuItemRun);
        // Pause
        menuItemPause = new JMenuItem("Pause");
        menuItemPause.addActionListener(l -> {
            simulation.stopSimulation();
        });
        menuMod.add(menuItemPause);
        // Draw
        menuItemDraw = new JMenuItem("Draw");
        menuItemDraw.addActionListener(l -> {
            gridPanelMainView.switchDrawMode();
        });
        menuMod.add(menuItemDraw);

        add(menuMod);
    }

    public void addMenuSpeed(Simulation simulation) {
        menuSpeed = new JMenu("Speed");
        // Faster
        menuItemFaster = new JMenuItem("Faster");
        menuItemFaster.addActionListener(l -> {
            simulation.decreaseRefreshTime();
        });
        menuSpeed.add(menuItemFaster);
        // Slower
        menuItemSlower = new JMenuItem("Slower");
        menuItemSlower.addActionListener(l -> {
            simulation.increaseRefreshTime();
        });
        menuSpeed.add(menuItemSlower);

        add(menuSpeed);
    }

    public void addMenuWindow(Simulation simulation, JDesktopPane jDesktopPane) {
        menuWindow = new JMenu("Window");
        // Clone
        menuItemClone = new JMenuItem("Clone");
        menuItemClone.addActionListener(l -> {
            jDesktopPane.add(new IFrameMain(simulation, jDesktopPane));
        });
        menuWindow.add(menuItemClone);
        // View
        menuItemView = new JMenuItem("View");
        menuItemView.addActionListener(l -> {
            jDesktopPane.add(new IFrameView(simulation));
        });
        menuWindow.add(menuItemView);

        add(menuWindow);
    }

    public void addMenuForm(Simulation simulation) {
        menuForm = new JMenu("Form");
        // Reset
        menuItemReset = new JMenuItem("Reset");
        menuItemReset.addActionListener(l -> {
            simulation.createDeadCells();
        });
        menuForm.add(menuItemReset);
        // Random
        menuItemRandom = new JMenuItem("Random");
        menuItemRandom.addActionListener(l -> {
            simulation.createRandomCells();
        });
        menuForm.add(menuItemRandom);
        // Glider
        menuItemGlider = new JMenuItem("Glider");
        menuItemGlider.addActionListener(l -> {
            simulation.createGliderCells();
        });
        menuForm.add(menuItemGlider);
        // LWWS
        menuItemLWWS = new JMenuItem("LWWS");
        menuItemLWWS.addActionListener(l -> {
            simulation.createLWSS();
        });
        menuForm.add(menuItemLWWS);
        // Pento
        menuItemPento = new JMenuItem("Pento");
        menuItemPento.addActionListener(l -> {
            simulation.createPento();
        });
        menuForm.add(menuItemPento);

        add(menuForm);
    }

    public void addMenuCell(GridPanelBasic gridPanel, IFrameBasic mainView) {
        menuCell = new JMenu("Cell");
        // Increase
        menuItemIncrease = new JMenuItem("Increase");
        menuItemIncrease.addActionListener(l -> {
            gridPanel.increaseCellSize();
            mainView.setWindowSize(gridPanel);
        });
        menuCell.add(menuItemIncrease);
        // Decrease
        menuItemDecrease = new JMenuItem("Decrease");
        menuItemDecrease.addActionListener(l -> {
            gridPanel.decreaseCellSize();
            mainView.setWindowSize(gridPanel);
        });
        menuCell.add(menuItemDecrease);

        add(menuCell);
    }
}
