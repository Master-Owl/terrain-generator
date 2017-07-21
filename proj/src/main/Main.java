package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import perlinNoise.NoiseGenerator;
import perlinNoise.NoiseInterpreter;

public class Main {

	private static BufferedImage image;
	
	public static void main(String[] args) {
		Random rand = new Random(new Date().getTime());
		JFrame window = new JFrame("Perlin Noise Generator");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(500, 500);
		
		NoiseGenerator generator = new NoiseGenerator(rand.nextInt(), window.getSize().width, window.getSize().height);
		image = perlinNoise.Image
				.RenderImage(NoiseInterpreter.GetGradientMap(generator.generatePerlinNoise(8), Color.white, Color.black));
		
		JLabel generateButton = new JLabel("Generate New");
		generateButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				generator.setSeed(rand.nextInt());
				image = perlinNoise.Image
						.RenderImage(NoiseInterpreter.GetGradientMap(generator.generatePerlinNoise(8), Color.white, Color.black));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JPanel panel = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D)g;
				g2d.clearRect(0, 0, getWidth(), getHeight());
				g2d.drawImage(image, 0, 0, this);				
			}
		};
		panel.setPreferredSize(window.getSize());
		
		window.getContentPane().add(panel);
		//window.getContentPane().add(generateButton);
		window.pack();
		window.setVisible(true);
	}
}
