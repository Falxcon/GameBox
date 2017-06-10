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

        setChanged();
        notifyObservers();
    }

    public void saveMap(String mapName){

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
