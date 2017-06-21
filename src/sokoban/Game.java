package sokoban;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tomwi on 21.06.2017.
 */
public class Game implements Serializable{
    List<Level> levels;
    int currentLevel;
    String name;

    Game(String name, int currentLevel, List<Level> levels){
        this.levels = levels;
        this.currentLevel = currentLevel;
        this.name = name;
    }
}
