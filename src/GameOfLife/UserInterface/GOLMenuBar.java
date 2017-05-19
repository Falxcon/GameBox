package GameOfLife.UserInterface;

import GameOfLife.Model.GOLSimulation;

import javax.swing.*;

public class GOLMenuBar extends JMenuBar {

    private JMenu menuMod, menuSpeed, menuWindow, menuForm, menuCell;
    // menuMod
    private JMenuItem menuItemRun, menuItemPause, menuItemDraw;
    // menuSpeed
    private JMenuItem menuItemFaster, menuItemSlower;
    // menuWindow
    private JMenuItem menuItemClone, menuItemView, menuItemSize;
    // menuForm
    private JMenuItem menuItemReset, menuItemRandom, menuItemGlider, menuItemLWWS, menuItemPento;
    // menuCell
    private JMenuItem menuItemIncrease, menuItemDecrease;

    private GOLGridPanel gridPanel;
    private GOLSimulation simulation;
    private GOLMain main;

    public GOLMenuBar(GOLSimulation simulation, GOLGridPanel gridPanel, GOLMain main) {
        this.gridPanel = gridPanel;
        this.simulation = simulation;
        this.main = main;

        initMenuMod();
        initMenuSpeed();
        initMenuWindow();
        initMenuForm();
        initMenuCell();
    }

    private void initMenuMod() {
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
            gridPanel.switchDrawMode();
        });
        menuMod.add(menuItemDraw);
    }

    private void initMenuSpeed() {
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
    }

    private void initMenuWindow() {
        menuWindow = new JMenu("Window");
        // Clone
        menuItemClone = new JMenuItem("Clone");
        menuItemClone.addActionListener(l -> {

        });
        menuWindow.add(menuItemClone);
        // GOLMain
        menuItemView = new JMenuItem("GOLMain");
        menuItemView.addActionListener(l -> {

        });
        menuWindow.add(menuItemView);
        // Size
        menuItemSize = new JMenuItem("Size");
        menuItemSize.addActionListener(l -> {
        });
        menuWindow.add(menuItemSize);
    }

    private void initMenuForm() {
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
    }

    private void initMenuCell() {
        menuCell = new JMenu("Cell");
        // Increase
        menuItemIncrease = new JMenuItem("Increase");
        menuItemIncrease.addActionListener(l -> {
            gridPanel.increaseCellSize();
            main.setWindowSize();
        });
        menuCell.add(menuItemIncrease);
        // Decrease
        menuItemDecrease = new JMenuItem("Decrease");
        menuItemDecrease.addActionListener(l -> {
            gridPanel.decreaseCellSize();
            main.setWindowSize();
        });
        menuCell.add(menuItemDecrease);
    }

    public void addMenuMod() {
        add(menuMod);
    }

    public void addMenuSpeed() {
        add(menuSpeed);
    }

    public void addMenuWindow() {
        add(menuWindow);
    }

    public void addMenuForm() {
        add(menuForm);
    }

    public void addMenuCell() {
        add(menuCell);
    }
}
