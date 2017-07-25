package window;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import perlinNoise.NoiseGenerator;
import perlinNoise.NoiseInterpreter;
import perlinNoise.Settings;

public class Window {
	private static BufferedImage image;
	private static NoiseGenerator generator;
	private JFrame window;
	private JMenuBar menuBar;
	private String windowName;
	private int windowWidth = 1200;
	private int windowHeight = 1000;
	
	private Color gradientStart = Color.white;
	private Color gradientEnd = Color.black;
	
	public Window(String windowName) {
		this.windowName = windowName;
	}
	
	public void setDimensions(int width, int height) {
		windowWidth = width;
		windowHeight = height;
	}
	
	public void setDimensions(Dimension d) {
		setDimensions(d.height, d.width);
	}
	
	public void init() {
		Random rand = new Random(new Date().getTime());
		JButton generate = new JButton("Generate");
		window = new JFrame(windowName); 
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(windowWidth, windowHeight);
		initMenu();
		
		generator = new NoiseGenerator(rand.nextLong(), windowWidth / 4, windowHeight / 4);
		image = perlinNoise.Image
				.RenderImage(NoiseInterpreter
						.GetGradientMap(generator
								.generatePerlinNoise(generator.getSettings().getOctaves()), gradientStart, gradientEnd));
		
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
		
		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D)g;
				g2d.clearRect(0, 0, getWidth(), getHeight());
				g2d.drawImage(image, 0, 0, this);				
			}
		};
		
		panel.setPreferredSize(window.getSize());		
		generate.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				panel.repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				generator.setSeed(rand.nextLong());
				generateNewImageGradient(Color.white, Color.black);
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

		panel.add(generate);
		window.getContentPane().add(panel);
		window.pack();
		window.setVisible(true);
	}

	
	private void initMenu() {
		menuBar = new JMenuBar();
		JMenu prefs = new JMenu("Preferences");
		menuBar.add(prefs);
		
		JMenuItem params = new JMenuItem("Noise Parameters");
		params.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				showPreferences();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		prefs.add(params);
		window.setJMenuBar(menuBar);
	}
	
	private void showPreferences() {
		JFrame newFrame = new JFrame("Preferences");
		final JPanel preferencesPanel = new JPanel();
		final JTabbedPane preferences = new JTabbedPane();
		preferences.setPreferredSize(new Dimension(350, 250));		
		final Settings newSettings = new Settings(generator.getSettings());
		int min_octaves = 2;
		int max_octaves = 12;
		int init_octaves = newSettings.getOctaves();
		
		JSlider octaves = new JSlider(JSlider.HORIZONTAL, min_octaves, max_octaves, init_octaves);
		octaves.setMajorTickSpacing(2);
		octaves.setMinorTickSpacing(1);
		octaves.setPaintLabels(true);
		octaves.setPaintTicks(true);
		octaves.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider)e.getSource();
				newSettings.setOctaves(slider.getValue());
			}
		});
		
		int min_width = 150;
		int max_width = 500;
		int init_width = newSettings.getArrWidth();
		if (init_width < min_width) init_width = min_width;
		if (init_width > max_width) init_width = max_width;
		
		int min_height = min_width;
		int max_height = max_width;
		int init_height = newSettings.getArrHeight();
		if (init_height < min_height) init_height = min_height;
		if (init_height > max_height) init_height = max_height;
		
		JSlider width = new JSlider(JSlider.HORIZONTAL, min_width, max_width, init_width);
		width.setMajorTickSpacing(50);
		width.setMinorTickSpacing(10);
		width.setPaintLabels(true);
		width.setPaintTicks(true);
		width.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider)e.getSource();
				newSettings.setArrWidth(slider.getValue());
			}
		});
		JSlider height = new JSlider(JSlider.HORIZONTAL, min_height, max_height, init_height);
		height.setMajorTickSpacing(50);
		height.setMinorTickSpacing(10);
		height.setPaintLabels(true);
		height.setPaintTicks(true);
		height.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider)e.getSource();
				newSettings.setArrHeight(slider.getValue());
			}
		});
		
		int min_persist = 0;
		int max_persist = 100;
		int init_persist = (int)(newSettings.getPersistance() * 100);
		
		JSlider persistance = new JSlider(JSlider.HORIZONTAL, min_persist, max_persist, init_persist);
		persistance.setMajorTickSpacing(50);
		persistance.setMinorTickSpacing(10);
		persistance.setPaintLabels(true);
		persistance.setPaintTicks(true);
		persistance.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider)e.getSource();
				newSettings.setPersistance((float)(slider.getValue() / 100.0f));
			}
		});
	
		JTextField seed = new JTextField(16);
		seed.setText(String.valueOf(newSettings.getSeed()));
		seed.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				long seedNum = Long.parseLong(seed.getText());
				newSettings.setSeed(seedNum);
			}
		});
		
		JButton apply = new JButton("Apply");
		apply.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				generator.changeSettings(newSettings);
				newFrame.setVisible(false);
				generateNewImageGradient(Color.white, Color.black);
				window.repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		
		JPanel seedTab = new JPanel();
		seedTab.add(seed);
		
		preferences.addTab("Seed", seedTab);
		preferences.addTab("Width", width);
		preferences.addTab("Height", height);
		preferences.addTab("Octaves", octaves);
		preferences.addTab("Persistance", persistance);
				
		preferencesPanel.setPreferredSize(
				new Dimension(preferences.getPreferredSize().width, preferences.getPreferredSize().height + 40));
		preferencesPanel.add(preferences);
		preferencesPanel.add(apply);
		
		newFrame.getContentPane().add(preferencesPanel);
		newFrame.pack();
		newFrame.setVisible(true);
	}
	
	private void generateNewImageGradient(Color c1, Color c2) {
		image = perlinNoise.Image
				.RenderImage(NoiseInterpreter
						.GetGradientMap(generator
								.generatePerlinNoise(generator.getSettings().getOctaves()), c1, c2));	
	}
	
}
