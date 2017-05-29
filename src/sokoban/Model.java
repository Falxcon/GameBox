package sokoban;

import java.util.Observable;

/**
 * Created by tomwi on 29.05.2017.
 */
public class Model extends Observable {

    Field[][] board;


    Model(){

    }


    public void loadMap(String mapName){

    }

    public void saveMap(String mapName){

    }


    public Field[][] getBoard() {
        return board;
    }
}
