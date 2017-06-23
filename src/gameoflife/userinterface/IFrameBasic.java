package gameoflife.userinterface;

import javax.swing.*;
/*
* @author Valentin Lutz
* */

public class IFrameBasic extends JInternalFrame {

    public IFrameBasic() {
        super("Game of Life", false, true);
    }

    public void setWindowSize(GridPanelBasic gridPanel) {
        setSize(gridPanel.getWidth() + getInsets().left + getInsets().right,
                gridPanel.getHeight() + getInsets().top + getInsets().bottom + 46);
    }
}
