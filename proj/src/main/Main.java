package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import perlinNoise.NoiseGenerator;
import perlinNoise.NoiseInterpreter;

public class Main {

	private static BufferedImage image;
	
	public static void main(String[] args) {
		Random rand = new Random(new Date().getTime());
		JFrame window = new JFrame("Perlin Noise Generator"); 
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1200, 1000);
		
		NoiseGenerator generator = new NoiseGenerator(rand.nextInt(), window.getSize().width / 4, window.getSize().height / 4);
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
//		
//		window.addComponentListener(new ComponentListener() {
//			
//			@Override
//			public void componentShown(ComponentEvent e) {
//			}
//			
//			@Override
//			public void componentResized(ComponentEvent e) {
//				// TODO Optimize
//				panel.setPreferredSize(window.getSize());
//				generator.setDimensions(window.getSize());
//				image = perlinNoise.Image
//						.RenderImage(NoiseInterpreter.GetGradientMap(generator.generatePerlinNoise(6), Color.white, Color.black));
//				panel.repaint();
//				window.validate();
//			}
//			
//			@Override
//			public void componentMoved(ComponentEvent e) {
//			}
//			
//			@Override
//			public void componentHidden(ComponentEvent e) {
//			}
//		});
		
		window.getContentPane().add(panel);
		window.pack();
		window.setVisible(true);
	}
}
