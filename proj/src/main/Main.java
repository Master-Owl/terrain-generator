package main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * The class to pull all the other classes together and create a terrain
 */
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Window window = new Window("Terrain", 1200, 500);
        window.init();
        window.show();
    }
}