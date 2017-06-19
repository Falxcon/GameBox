package sokoban;

import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * Created by tomwi on 29.05.2017.
 */
public class Model extends Observable {

    Field[][] board, previousBoard;

    int width, height;
    boolean isRunning;
    String currentMap;

    final String mapsDirectory = "sokoban maps/";


    Model(){
        width = 0;
        height = 0;
        isRunning = false;
    }

    public void movePlayer(int addX, int addY){
        if(!isRunning) return;

        int playerX = -1, playerY = -1;
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                if(board[x][y] == Field.PLAYER || board[x][y] == Field.POT){
                    playerX = x;
                    playerY = y;
                    break;
                }
            }
        }

        if(playerX == -1 || playerY == -1) return;
        Field playerField = board[playerX][playerY];
        Field nextField = board[playerX + addX][playerY + addY];

        if(nextField == Field.WALL) return;

        if(nextField == Field.EMPTY || nextField == Field.TARGET){
            arrayCopy(board, previousBoard);

            if(nextField == Field.EMPTY) board[playerX + addX][playerY + addY] = Field.PLAYER;
            else board[playerX + addX][playerY + addY] = Field.POT;
            if(playerField == Field.PLAYER) board[playerX][playerY] = Field.EMPTY;
            else board[playerX][playerY] = Field.TARGET;
        }
        else if(nextField == Field.OBJECT || nextField == Field.OOT){

            Field afterNextField = board[playerX + addX + addX][playerY + addY + addY];
            if(afterNextField == Field.EMPTY || afterNextField == Field.TARGET){
                arrayCopy(board, previousBoard);

                if(afterNextField == Field.EMPTY) board[playerX + addX + addX][playerY + addY + addY] = Field.OBJECT;
                else board[playerX + addX + addX][playerY + addY + addY] = Field.OOT;
                if(nextField == Field.OBJECT) board[playerX + addX][playerY + addY] = Field.PLAYER;
                else board[playerX + addX][playerY + addY] = Field.POT;
                if(playerField == Field.PLAYER) board[playerX][playerY] = Field.EMPTY;
                else board[playerX][playerY] = Field.TARGET;
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
                if(board[x][y] == Field.OBJECT) solved = false;
            }
        }

        if(solved){
            isRunning = false;
            JOptionPane.showMessageDialog(null, "You solved this puzzle!", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void undo(){
        if(isRunning) {
            arrayCopy(previousBoard, board);
            setChanged();
            notifyObservers();
        }
    }

    public void loadMap(String mapName){

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

        List<String> lines = new ArrayList<>();
        width = inputLine.length();
        height = 0;

        while(inputLine != null){
            lines.add(inputLine);
            height++;
            if(inputLine.length() > width) width = inputLine.length();
            try {
                inputLine = fileInput.readLine();
            } catch (IOException e){
                break;
            }
        }

        board = new Field[width][height];
        previousBoard = new Field[width][height];
        Iterator<String> iterator = lines.iterator();

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

        arrayCopy(board, previousBoard);
        isRunning = true;
        setChanged();
        notifyObservers();
    }

    public void saveMap(String mapName){

    }

    public static void arrayCopy(Field[][] aSource, Field[][] aDestination) {
        for (int i = 0; i < aSource.length; i++) {
            System.arraycopy(aSource[i], 0, aDestination[i], 0, aSource[i].length);
        }
    }

    public Field getFieldByCoordinate(int col, int row){
        if(board[col][row] == null) return Field.EMPTY;
        else return board[col][row];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
