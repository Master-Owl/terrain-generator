package window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import perlinNoise.NoiseGenerator;
import perlinNoise.NoiseInterpreter;

public class Window {
	private static BufferedImage image;
	JFrame window;
	private JMenuBar menuBar;
	private String windowName;
	private int windowWidth = 1200;
	private int windowHeight = 1000;
	
	public Window(String windowName) {
		this.windowName = windowName;
	}
	
	public void init() {
		Random rand = new Random(new Date().getTime());
		window = new JFrame(windowName); 
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(windowWidth, windowHeight);
		initMenu();
		
		NoiseGenerator generator = new NoiseGenerator(rand.nextInt(), windowWidth / 4, windowHeight / 4);
		image = perlinNoise.Image
				.RenderImage(NoiseInterpreter.GetGradientMap(generator.generatePerlinNoise(8), Color.white, Color.black));
//		Color[] colorarr = new Color[5];
//		colorarr[0] = Color.white;
//		colorarr[1] = Color.lightGray;
//		colorarr[2] = Color.green;
//		colorarr[3] = Color.cyan;
//		colorarr[4] = Color.black;
//		float[] cutoffs = new float[4];
//		cutoffs[0] = 0.23f;
//		cutoffs[1] = 0.44f;
//		cutoffs[2] = 0.60f;
//		cutoffs[3] = 0.78f;
//		image = perlinNoise.Image
//				.RenderImage(NoiseInterpreter.GetColorMap(generator.generatePerlinNoise(8), colorarr, cutoffs));
		
		JPanel panel = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D)g;
				g2d.clearRect(0, 0, getWidth(), getHeight());
				g2d.drawImage(image, 0, 0, this);				
			}
		};
		panel.setPreferredSize(window.getSize());
		
		panel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				panel.repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				generator.setSeed(rand.nextInt());
				image = perlinNoise.Image
						.RenderImage(NoiseInterpreter.GetGradientMap(generator.generatePerlinNoise(8), Color.white, Color.black));				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		
		window.getContentPane().add(panel);
		window.pack();
		window.setVisible(true);
	}
	
	public void show() {  }
	
	private void initMenu() {
		menuBar = new JMenuBar();
		JMenu prefs = new JMenu("Preferences");
		menuBar.add(prefs);
		
		JMenu params = new JMenu("Noise Parameters");
		JMenuItem octaves = new JMenuItem("Number of Octaves");
		params.add(octaves);
		prefs.add(params);
		window.setJMenuBar(menuBar);
	}
	
}
