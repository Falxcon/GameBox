package rainbow;

import javax.swing.*;

/*
* @author Tom Wittal, Sebastian Glück, Valentin Lutz
* */

public class IFrameBasic extends JInternalFrame{

    JButton bNewWindow;

    JDesktopPane jDesktopPane;

    public IFrameBasic(JDesktopPane jdp) {
        super("Rainbow", false, true);

        jDesktopPane = jdp;

        initButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(200, 100);
        setVisible(true);
    }

    private void initButton() {//Methode zum Starten eines neuen Regenbogen Fensters
        bNewWindow = new JButton("Neues Fenster");
        bNewWindow.addActionListener(l -> {
            jDesktopPane.add(new IFrameRainbow());
        });
        add(bNewWindow);
    }
}
