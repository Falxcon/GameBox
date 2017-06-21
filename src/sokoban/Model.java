package sokoban;

import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * Created by tomwi on 29.05.2017.
 */
public class Model extends Observable {

    Field[][] currentBoard, previousBoard;
    List<Level> levels;

    int width, height, currentLvl;
    boolean isRunning;

    final String mapsDirectory = "src/sokoban/maps/";

    View view;

    Model(View view){
        this.view = view;
        levels = new LinkedList<>();
        width = 0;
        height = 0;
        isRunning = false;
    }

    public void movePlayer(int addX, int addY){
        if(!isRunning) return;

        int playerX = -1, playerY = -1;
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                if(currentBoard[x][y] == Field.PLAYER || currentBoard[x][y] == Field.POT){
                    playerX = x;
                    playerY = y;
                    break;
                }
            }
        }

        if(playerX == -1 || playerY == -1) return;
        Field playerField = currentBoard[playerX][playerY];
        Field nextField = currentBoard[playerX + addX][playerY + addY];

        if(nextField == Field.WALL) return;

        if(nextField == Field.EMPTY || nextField == Field.TARGET){
            arrayCopy(currentBoard, previousBoard);

            if(nextField == Field.EMPTY) currentBoard[playerX + addX][playerY + addY] = Field.PLAYER;
            else currentBoard[playerX + addX][playerY + addY] = Field.POT;
            if(playerField == Field.PLAYER) currentBoard[playerX][playerY] = Field.EMPTY;
            else currentBoard[playerX][playerY] = Field.TARGET;
        }
        else if(nextField == Field.OBJECT || nextField == Field.OOT){

            Field afterNextField = currentBoard[playerX + addX + addX][playerY + addY + addY];
            if(afterNextField == Field.EMPTY || afterNextField == Field.TARGET){
                arrayCopy(currentBoard, previousBoard);

                if(afterNextField == Field.EMPTY) currentBoard[playerX + addX + addX][playerY + addY + addY] = Field.OBJECT;
                else currentBoard[playerX + addX + addX][playerY + addY + addY] = Field.OOT;
                if(nextField == Field.OBJECT) currentBoard[playerX + addX][playerY + addY] = Field.PLAYER;
                else currentBoard[playerX + addX][playerY + addY] = Field.POT;
                if(playerField == Field.PLAYER) currentBoard[playerX][playerY] = Field.EMPTY;
                else currentBoard[playerX][playerY] = Field.TARGET;
            }
        }

        setChanged();
        notifyObservers();
        checkSolved();
    }

    public void checkSolved(){
        boolean solved = true;
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                if(currentBoard[x][y] == Field.OBJECT) solved = false;
            }
        }

        if(solved){
            isRunning = false;
            JOptionPane.showMessageDialog(null, "You solved this puzzle!", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
            loadLevel(currentLvl + 1);
        }
    }

    public void undo(){
        if(isRunning) {
            arrayCopy(previousBoard, currentBoard);
            setChanged();
            notifyObservers();
        }
    }

    public void restart(){
        loadLevel(currentLvl);
    }

    public void previousLevel(){
        if(currentLvl > 1) {
            loadLevel(currentLvl - 1);
        }
    }

    public void nextLevel(){
        loadLevel(currentLvl + 1);
    }

    public void loadMap(String mapName){

        levels = new LinkedList<>();

        File file = new File(mapsDirectory + mapName);
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (Exception e){
            System.out.println("File not Found");
            return;
        }
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader fileInput = new BufferedReader(inputStreamReader);

        String inputLine;
        try {
            inputLine = fileInput.readLine();
        } catch (IOException e){
            System.out.println("File empty");
            return;
        }

        while(inputLine != null) {

            List<String> lines = new ArrayList<>();
            int number = 0;
            String name = "";
            int levelWidth = inputLine.length();
            int levelHeight = 0;

            while (inputLine != null && !inputLine.equals("")) {

                if(inputLine.contains("Level")){
                    number = Integer.parseInt(inputLine.substring(inputLine.indexOf(' ') + 1));
                } else if(inputLine.contains("'")){
                    name = inputLine.substring(1, inputLine.length() -1);
                } else {
                    lines.add(inputLine);
                    levelHeight++;
                    if (inputLine.length() > levelWidth) levelWidth = inputLine.length();
                }

                try {
                    inputLine = fileInput.readLine();
                } catch (IOException e) {
                    break;
                }
            }

            readLevel(name, number, levelWidth, levelHeight, lines);

            try {
                inputLine = fileInput.readLine();
            } catch (IOException e) {
                break;
            }
        }

        loadLevel(1);
    }

    public void loadLevel(int number){
        Level level;
        try {
            level = levels.get(number - 1);
        } catch (Exception e){
            System.out.println("no next level found");
            return;
        }

        currentLvl = level.getNumber();
        width = level.getWidth();
        height = level.getHeight();
        currentBoard = new Field[width][height];
        previousBoard = new Field[width][height];
        arrayCopy(level.getBoard(), currentBoard);
        arrayCopy(level.getBoard(), previousBoard);

        view.setTitle("Sokoban Level " + level.getNumber() + " " + level.getName());

        isRunning = true;
        view.initGrid(width, height);
        setChanged();
        notifyObservers();
    }

    private void readLevel(String name, int number, int width, int height, List<String> lines){
        Iterator<String> iterator = lines.iterator();
        Field[][] board = new Field[width][height];
        for(int row = 0; row < height; row++){

            String line = iterator.next();
            while(line.length() < width){
                line += " ";
            }
            char[] characters = line.toCharArray();

            for(int col = 0; col < width; col++){
                switch(characters[col]){
                    case '#':
                        board[col][row] = Field.WALL;
                        break;
                    case ' ':
                        board[col][row] = Field.EMPTY;
                        break;
                    case '@':
                        board[col][row] = Field.PLAYER;
                        break;
                    case '.':
                        board[col][row] = Field.TARGET;
                        break;
                    case '$':
                        board[col][row] = Field.OBJECT;
                        break;
                    case '*':
                        board[col][row] = Field.OOT;
                        break;
                    case '+':
                        board[col][row] = Field.POT;
                        break;
                    default:
                        board[col][row] = Field.EMPTY;
                        break;

                }
            }
        }

        levels.add(new Level(name, number, width, height, board));
    }

    public void loadGame(){

        File folder = new File("Saved Games");
        File[] listOfFiles = folder.listFiles();
        String[] fileNames = new String[listOfFiles.length];
        for(int i = 0; i < fileNames.length; i++){
            fileNames[i] = listOfFiles[i].getName().substring(0, listOfFiles[i].getName().indexOf('.'));
        }

        String picked = (String)JOptionPane.showInputDialog(view, "Pick Saved Game:"
                , "Load Game", JOptionPane.QUESTION_MESSAGE
                , null, fileNames, fileNames[0]);

        ObjectInputStream inputStream;
        Game game;
        try {
            inputStream = new ObjectInputStream(new FileInputStream("Saved Games/" + picked + ".ser"));
            game = (Game)inputStream.readObject();
        } catch (Exception e){
            return;
        }

        levels = game.levels;
        currentLvl = game.currentLevel;
        loadLevel(currentLvl);
    }

    public void saveGame(){
        try {
            Level level = new Level(levels.get(currentLvl - 1).name, currentLvl, width, height, currentBoard);
            List<Level> saveLevels = levels;
            saveLevels.set(currentLvl - 1, level);
            String input = JOptionPane.showInputDialog("Enter the name of the saved Game", level.name);
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("Saved Games/" + input + ".ser"));
            os.writeObject(new Game(input, currentLvl, saveLevels));
            os.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public static void arrayCopy(Field[][] aSource, Field[][] aDestination) {
        for (int i = 0; i < aSource.length; i++) {
            System.arraycopy(aSource[i], 0, aDestination[i], 0, aSource[i].length);
        }
    }

    public Field getFieldByCoordinate(int col, int row){
        if(currentBoard[col][row] == null) return Field.EMPTY;
        else return currentBoard[col][row];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
