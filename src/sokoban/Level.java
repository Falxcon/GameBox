package sokoban;

import java.io.Serializable;

/**
 * Created by tomwi on 19.06.2017.
 */
public class Level implements Serializable{

    Field[][] board;
    String name;
    int number;
    int width;
    int height;

    Level(String name, int number, int width, int height, Field[][] board){
        this.number = number;
        this.name = name;
        this.board = board;
        this.width = width;
        this.height = height;
    }

    public Field[][] getBoard() {
        return board;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
