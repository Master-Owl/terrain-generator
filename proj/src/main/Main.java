package main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import window.Window;

public class Main {

	public static void main(String[] args) {
        try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		window.Window window = new Window("Perlin Noise Generator");
		window.setDimensions(800, 800);
		window.init();
	}
}
