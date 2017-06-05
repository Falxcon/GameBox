package sokoban;

import java.io.*;
import java.util.*;

/**
 * Created by tomwi on 29.05.2017.
 */
public class Model extends Observable {

    Field[][] board;

    int width, height;

    final String mapsDirectory = "sokoban maps/";


    Model(){

    }


    public void loadMap(String mapName){

        File file = new File(mapsDirectory + mapName + ".txt");
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

        System.out.println("width: " + width + " height: " + height);

        board = new Field[width][height];
        Iterator<String> iterator = lines.iterator();
        int rowIndex = 0;
        while(iterator.hasNext()){
            String row = iterator.next();
            int colIndex = 0;
            for(char ch : row.toCharArray()){
                switch(ch){
                    case '#':
                        board[rowIndex][colIndex] = Field.WALL;
                        break;
                    case ' ':
                        board[rowIndex][colIndex] = Field.EMPTY;
                        break;
                    case '@':
                        board[rowIndex][colIndex] = Field.PLAYER;
                        break;
                    case '.':
                        board[rowIndex][colIndex] = Field.TARGET;
                        break;
                    case '$':
                        board[rowIndex][colIndex] = Field.OBJECT;
                        break;
                    case '*':
                        board[rowIndex][colIndex] = Field.OOT;
                        break;
                    case '+':
                        board[rowIndex][colIndex] = Field.POT;
                        break;
                    default:
                        board[rowIndex][colIndex] = Field.EMPTY;
                        break;

                }
                colIndex++;
            }
            rowIndex++;
        }

        setChanged();
        notifyObservers();
    }

    public void saveMap(String mapName){

    }


    public Field[][] getBoard() {
        return board;
    }

    public Field getFieldByCoords(int row, int col){
        if(board[row][col] == null) return Field.EMPTY;
        else return board[row][col];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
