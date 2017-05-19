package GameOfLife.Model;

import java.util.*;

public class Simulation extends Observable {

    private boolean[][] grid;

    private int refreshTime;
    private Thread thread;

    public Simulation(int width, int height) {
        grid = new boolean[width][height];

        refreshTime = 256;
        thread = new Thread();
    }

    /**
     * alle Zellen werden in tote Zellen gesetzt
     */
    public void createDeadCells() {
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                grid[x][y] = false;
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * erstellt zufällig lebende Zellen
     */
    public void createRandomCells() {
        Random random = new Random();
        for(int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                grid[x][y] = random.nextBoolean();
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * erstellt einen Glider in der Mitte des Grids
     */
    public void createGliderCells() {
        createDeadCells();
        int x = grid.length / 2;
        int y = grid[0].length / 2;
        setCell(x + 1, y, true);
        setCell(x, y - 1, true);
        for (int i = -1; i < 2; i++) {
            setCell(x + i, y + 1, true);
        }
        setChanged();
        notifyObservers();
    }

    /**
     * erstellt ein LightweightSpaceship in der Mitt des Grids
     */
    public void createLWSS() {
        createDeadCells();
        int x = grid.length / 2;
        int y = grid[0].length / 2;
        setCell(x - 2, y, true);
        setCell(x - 2, y + 2, true);
        setCell(x + 1, y + 2, true);
        for (int i = -1; i < 3; i++) {
            setCell(x + i, y - 1, true);
        }
        for (int i = 0; i < 2; i++) {
            setCell(x + 2, y + i, true);
        }
        setChanged();
        notifyObservers();
    }

    /**
     * erstellt ein r-Pentomino in der Mitte des Grids
     */
    public void createPento() {
        createDeadCells();
        int x = grid.length / 2;
        int y = grid[0].length / 2;
        setCell(x - 1, y, true);
        setCell(x + 1, y - 1, true);
        for (int i = -1; i < 2; i++) {
            setCell(x, y + i, true);
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Setzt die Zelle an den Koordinaten x,y auf lebend
     * @param x Grid Koordinate
     * @param y Grid Koordinate
     */
    public void setCell(int x, int y, boolean b) {
        grid[x][y] = b;
    }

    /**
     * berrechnet die nÃ¤chste Generation des GameOfLife
     */
    public void simulateNextGeneration() {
        boolean nextGrid[][] = new boolean[grid.length][grid[0].length];

        for(int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                int mx = x - 1;
                if(mx < 0) mx = grid.length - 1;
                int my = y - 1;
                if(my < 0) my = grid[x].length - 1;
                int gx = (x + 1) % grid.length;
                int gy = (y + 1) % grid[x].length;

                int neighborCounter = 0;
                if(grid[mx] [my]) neighborCounter++;
                if(grid[mx] [y]) neighborCounter++;
                if(grid[mx] [gy]) neighborCounter++;
                if(grid[x] [my]) neighborCounter++;
                if(grid[x] [gy]) neighborCounter++;
                if(grid[gx] [my]) neighborCounter++;
                if(grid[gx] [y]) neighborCounter++;
                if(grid[gx] [gy]) neighborCounter++;

                if(neighborCounter < 2 || neighborCounter > 3) {
                    nextGrid[x] [y] = false;
                }
                else if(neighborCounter == 2) {
                    nextGrid[x] [y] = grid[x][y];
                }
                else {
                    nextGrid[x] [y] = true;
                }
            }
        }

        grid = nextGrid;
        setChanged();
        notifyObservers();
    }

    public void startSimulation() {
        if (!thread.isAlive()) {
            Runnable task = () -> {
                while (Thread.currentThread().isAlive()) {
                    simulateNextGeneration();
                    try {
                        Thread.sleep(refreshTime);
                    } catch (InterruptedException e) {
                        // Thread zerstört sich selbst
                        return;
                    }
                }
            };
            thread = new Thread(task);
            thread.start();
        }
    }

    public void stopSimulation() {
        thread.interrupt();
    }

    public void increaseRefreshTime() {
        refreshTime = refreshTime * 2;
    }

    public void decreaseRefreshTime() {
        refreshTime = refreshTime / 2 + 1;
    }

    public boolean[][] getGrid() {
        return grid;
    }
}
